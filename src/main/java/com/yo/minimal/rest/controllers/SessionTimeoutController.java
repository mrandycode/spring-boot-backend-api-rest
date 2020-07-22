package com.yo.minimal.rest.controllers;

import com.yo.minimal.rest.models.entity.SessionTimeout;
import com.yo.minimal.rest.models.services.interfaces.ISessionTimeoutServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"https://yo-minimal-web.herokuapp.com"})
@RestController
@RequestMapping("/api/session/timeout/")
public class SessionTimeoutController {

    @Autowired
    private ISessionTimeoutServices iSessionTimeoutServices;

    /***************************************
     * @param
     * @return Tiempo de inactividad
     ****************************************/
    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findSessionTimeout() {

        List<SessionTimeout> sessionTimeoutList;
        Map<String, Object> response = new HashMap<>();

        try {
            sessionTimeoutList = iSessionTimeoutServices.findAllSessionTimeout();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (sessionTimeoutList == null || sessionTimeoutList.size() < 1) {
            response.put("message", "No hay tiempo de inactividad de sesión existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("sessionTimeout", sessionTimeoutList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
