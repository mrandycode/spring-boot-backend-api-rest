package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.ForeignCurrency;
import com.yo.minimal.rest.models.services.interfaces.IForeignCurrencyServices;
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

@CrossOrigin(origins = {"https://yo-minimal-web.herokuapp.com"})
@RestController
@RequestMapping("/api/foreign/currency")
public class ForeignCurrencyRestController {

    @Autowired
    private IForeignCurrencyServices iForeignCurrencyServices;

    /***************************************
     * @param
     * @return Listado de Monedas Extranjeras
     ****************************************/
    @GetMapping("get/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findInvoiceAll() {

        List<ForeignCurrency> foreignCurrencies;
        Map<String, Object> response = new HashMap<>();

        try {
            foreignCurrencies = iForeignCurrencyServices.findAllForeignCurrency();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (foreignCurrencies == null || foreignCurrencies.size() < 1) {
            response.put("message", "No hay moneda extranjera existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("foreignCurrencies", foreignCurrencies);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Moneda Extranjera por Id
     ****************************************/
    @GetMapping("get/by/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findInvoiceById(@PathVariable Long id) {

        ForeignCurrency foreignCurrency;
        Map<String, Object> response = new HashMap<>();

        try {
            foreignCurrency = iForeignCurrencyServices.findByIdForeignCurrency(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (foreignCurrency == null) {
            response.put("message", "La moneda extranjera con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("foreignCurrency", foreignCurrency);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Guardar Moneda Extranjera
     ****************************************/
    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveForeignCurrency(@Valid String foreignCurrencyInput
            , @ModelAttribute ForeignCurrency foreignCurrencyDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        ForeignCurrency foreignCurrency = new ObjectMapper().readValue(foreignCurrencyInput, ForeignCurrency.class);
        ForeignCurrency foreignCurrencyNew;

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

            foreignCurrency.setCreateDate(new Date());
            foreignCurrency.setUser("acevallos");
            foreignCurrencyNew = iForeignCurrencyServices.saveForeignCurrency(foreignCurrency);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La moneda extranjera ha sido creado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("foreignCurrency", foreignCurrencyNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Actualizar Moneda Extranjera
     ****************************************/
    @PutMapping("put")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateForeignCurrency(@Valid String foreignCurrencyInput
            , @ModelAttribute ForeignCurrency foreignCurrencyDto
            , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        ForeignCurrency foreignCurrency = new ObjectMapper().readValue(foreignCurrencyInput, ForeignCurrency.class);
        ForeignCurrency foreignCurrencyNow = iForeignCurrencyServices.findByIdForeignCurrency(foreignCurrency.getId());
        ForeignCurrency foreignCurrencyUpdated;

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

            if (foreignCurrencyNow == null) {
                response.put("message", "No se encuentra la Moneda Extranjera con el ID:  " + foreignCurrency.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // TODO:"quitar";
            foreignCurrency.setUser("acevallos");
            foreignCurrency.setUpdateDate(new Date());
            foreignCurrencyUpdated = iForeignCurrencyServices.saveForeignCurrency(foreignCurrency);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La moneda extranjera " + foreignCurrencyUpdated.getId() + "-" + foreignCurrencyUpdated.getCurrencyType() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("foreignCurrency", foreignCurrencyUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}