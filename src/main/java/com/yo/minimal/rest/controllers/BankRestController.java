package com.yo.minimal.rest.controllers;

import com.yo.minimal.rest.models.entity.Bank;
import com.yo.minimal.rest.models.services.interfaces.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banks/")
public class BankRestController {

    @Autowired
    private IBankService iBankService;

    /***************************************
     * @return Listado de Bancos Nacionales
     ****************************************/
    @GetMapping("get/bank-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findBanks() {

        List<Bank> banks;
        Map<String, Object> response = new HashMap<>();

        try {
            banks = iBankService.findAllBanks();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (banks == null || banks.size() < 1) {
            response.put("message", "No hay bancos existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("banks", banks);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
