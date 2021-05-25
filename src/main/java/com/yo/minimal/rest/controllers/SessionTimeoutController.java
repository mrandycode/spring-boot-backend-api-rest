package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.SessionTimeout;
import com.yo.minimal.rest.models.services.interfaces.ISessionTimeoutServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/session/timeout/")
public class SessionTimeoutController {

    @Autowired
    private ISessionTimeoutServices iSessionTimeoutServices;

    /***************************************
     * @return Tiempo de inactividad
     ****************************************/
    @GetMapping("get/all-configuration")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findSessionTimeout() {

        List<SessionTimeout> sessionsTimeoutList;
        Map<String, Object> response = new HashMap<>();

        try {
            sessionsTimeoutList = iSessionTimeoutServices.findAllSessionTimeout();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (sessionsTimeoutList == null || sessionsTimeoutList.size() < 1) {
            response.put("message", "No hay tiempo de inactividad de sesión existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("sessionsTimeout", sessionsTimeoutList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param id Id para buscar la configuración de tiempo de sesión.
     * @return Tiempo de inactividad según el id
     ****************************************/
    @GetMapping("get/configuration/by/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findByIdSessionTimeout(@PathVariable Long id) {

        SessionTimeout sessionTimeout;
        Map<String, Object> response = new HashMap<>();

        try {
            sessionTimeout = iSessionTimeoutServices.findByIdSessionTimeout(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (sessionTimeout == null) {
            response.put("message", "La configuración de sesión con el ID" + id + " no existe en la base de daros");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("sessionTimeout", sessionTimeout);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param sessionTimeoutInput Objeto con información de datos para ser guardado
     * @return Guardar tiempo de sesión
     ****************************************/
    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveSessionTimeout(@Valid String sessionTimeoutInput
            , @ModelAttribute SessionTimeout sessionTimeoutDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        SessionTimeout sessionTimeout = new ObjectMapper().readValue(sessionTimeoutInput, SessionTimeout.class);
        SessionTimeout sessionTimeoutNew;

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getFieldErrors()
                    .stream()
                    .map(err ->
                            "El campo: '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errorList);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            sessionTimeout.setCreateDateTime(new Date());
            sessionTimeout.setUserCreate("acevallos");
            sessionTimeoutNew = iSessionTimeoutServices.saveSessionTimeout(sessionTimeout);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La moneda extranjera ha sido creado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("sessionTimeout", sessionTimeoutNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param sessionTimeoutInput Objeto con información de datos para ser guardado
     * @return Actualizar Tiempo de Sesión
     ****************************************/
    @PutMapping("put")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateForeignCurrency(@Valid String sessionTimeoutInput
            , @ModelAttribute SessionTimeout sessionTimeoutDto
            , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        SessionTimeout sessionTimeout = new ObjectMapper().readValue(sessionTimeoutInput, SessionTimeout.class);
        SessionTimeout sessionTimeoutNow = iSessionTimeoutServices.findByIdSessionTimeout(sessionTimeout.getId());
        SessionTimeout sessionTimeoutUpdated;

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getFieldErrors()
                    .stream()
                    .map(err ->
                            "El campo: '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errorList);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            if (sessionTimeoutNow == null) {
                response.put("message", "No se encuentra la configuración de tiempo de sesión con el ID:  " + sessionTimeout.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // TODO:"quitar";
            sessionTimeout.setUserCreate("acevallos");
            sessionTimeout.setUpdateDateTime(new Date());
            sessionTimeoutUpdated = iSessionTimeoutServices.saveSessionTimeout(sessionTimeout);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El timpo de sesión " + sessionTimeoutUpdated.getId() + "-" + sessionTimeoutUpdated.getRol() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("sessionTimeout", sessionTimeout);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
