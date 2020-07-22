package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.CClass;
import com.yo.minimal.rest.models.entity.Item;
import com.yo.minimal.rest.models.services.interfaces.IClassServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://yo-minimal-web.herokuapp.com"})
@RestController
@RequestMapping("/api/class/")
public class ClassRestController {

    @Autowired
    private IClassServices iClassServices;

    /***************************************
     * @param
     * @return Listado de clases de un producto
     ****************************************/
    @GetMapping("get/class-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAllClass() {

        List<CClass> classList;
        Map<String, Object> response = new HashMap<>();

        try {
            classList = iClassServices.findAllClass();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (classList == null || classList.size() < 1) {
            response.put("message", "No hay clases existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("classes", classList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return CClass por Id
     ****************************************/
    @GetMapping("get/class-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findClassById(@PathVariable Long id) {

        CClass aclass;
        Map<String, Object> response = new HashMap<>();

        try {
            aclass = iClassServices.findByIdClass(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (aclass == null) {
            response.put("message", "La Clase con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("class", aclass);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Guardar Clase
     ****************************************/
    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveClassById(   @Valid String classInput
                                            , @ModelAttribute CClass classDto
                                            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        CClass aClass = new ObjectMapper().readValue(classInput, CClass.class);
        CClass classNew;

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

            classNew = iClassServices.saveClass(aClass);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("class", classNew);
        response.put("message", "La Clase ha sido creada con exito");
        response.put("cod", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Actualizar una Clase
     ****************************************/
    @PutMapping("put")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateClassById( @Valid String classInput
                                         , @ModelAttribute Item itemDto
                                         , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        CClass aClass = new ObjectMapper().readValue(classInput, CClass.class);
        CClass classNow = iClassServices.findByIdClass(aClass.getId());
        CClass classUpdated;

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

            if (classNow == null) {
                response.put("message", "No se encuentra la clase con el ID:  " + aClass.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            classNow.setNameClass(aClass.getNameClass());
            classNow.setDescription(aClass.getDescription());

            classUpdated = iClassServices.saveClass(classNow);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("class", classNow);
        response.put("message", "La Clase " + classUpdated.getNameClass() + "-" + classUpdated.getDescription() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Cambiar status Clase - Delete
     ****************************************/
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("change/status")
    public ResponseEntity<?> updateItemStatusById(@Valid String classInput
            , @ModelAttribute CClass classDto
            , BindingResult bindingResult) throws IOException {


        Map<String, Object> response = new HashMap<>();
        CClass aClass = new ObjectMapper().readValue(classInput, CClass.class);

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

//            iClassServices.updateStatusClassById(aClass.getId(), aClass.getStatus());

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("class", aClass);
        response.put("message", "La Clase " + aClass.getId() + "-" + aClass.getNameClass() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}