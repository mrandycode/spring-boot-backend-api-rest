package com.yo.minimal.rest.controllers;

import com.yo.minimal.rest.DTO.JwtDto;
import com.yo.minimal.rest.DTO.LoginUserDto;
import com.yo.minimal.rest.DTO.NewUserDto;
import com.yo.minimal.rest.constants.enums.RoleName;
import com.yo.minimal.rest.models.entity.RoleUser;
import com.yo.minimal.rest.models.entity.User;
import com.yo.minimal.rest.models.services.IRoleUserServices;
import com.yo.minimal.rest.models.services.IUserServices;
import com.yo.minimal.rest.security.JWT.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserServices iUserServices;

    @Autowired
    IRoleUserServices iRoleUserServices;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/new")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getFieldErrors()
                    .stream()
                    .map(err ->
                            "El campo: '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errorList);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (iUserServices.existsByUserName(newUserDto.getUserName())) {
            response.put("message", "Nombre de Usuario ya existe: ");
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (iUserServices.existsByEmail(newUserDto.getEmail())) {
            response.put("message", "Correo ya existe: ");
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user =
                new User(newUserDto.getName(), newUserDto.getLastName(), newUserDto.getIdentificationId(),
                        newUserDto.getNacionality(), newUserDto.getUserName(), newUserDto.getEmail(),
                        passwordEncoder.encode(newUserDto.getPassword()));

        Set<String> rolesStr = newUserDto.getRoles();
        Set<RoleUser> roles = new HashSet<>();
        for (String rol : rolesStr) {
            switch (rol) {
                case "admin":
                    RoleUser rolAdmin = iRoleUserServices.findByRolName(RoleName.ROLE_ADMIN).get();
                    roles.add(rolAdmin);
                    break;
                default:
                    RoleUser rolUser = iRoleUserServices.findByRolName(RoleName.ROLE_USER).get();
                    roles.add(rolUser);
            }
        }
        user.setRoles(roles);
        iUserServices.save(user);

        response.put("message", "El usuario ha sido creado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {

            Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getFieldErrors()
                    .stream()
                    .map(err ->
                            "El campo: '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errorList);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getUserName(), loginUserDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDTO = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }
}
