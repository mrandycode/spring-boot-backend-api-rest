package com.yo.minimal.rest.controllers;

import com.yo.minimal.rest.models.entity.AttributesItem;
import com.yo.minimal.rest.models.services.interfaces.IAttributesItemsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/attributes/item")
public class AttributesItemRestController {

    @Autowired
    private IAttributesItemsServices iAttributesItemsServices;

    /***************************************
     * @param id Id del Atributo del item,
     * @return Item por Id
     ****************************************/
    @GetMapping("get/attribute-item-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findItemById(@PathVariable Long id) {

        AttributesItem attributesItem;
        Map<String, Object> response = new HashMap<>();

        try {
            attributesItem = iAttributesItemsServices.findByIdAttributesItem(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (attributesItem == null) {
            response.put("message", "El atributo de item con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("attributesItem", attributesItem);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}