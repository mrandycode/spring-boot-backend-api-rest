package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.dto.RequestAttributesItem;
import com.yo.minimal.rest.models.entity.AttributesItem;
import com.yo.minimal.rest.models.entity.Item;
import com.yo.minimal.rest.models.services.interfaces.IAttributesItemsServices;

import com.yo.minimal.rest.models.services.interfaces.IItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/attributes/item")
public class AttributesItemRestController {

    @Autowired
    private IAttributesItemsServices iAttributesItemsServices;

    @Autowired
    private IItemServices iItemServices;

    /***************************************
     * @param id Id del Atributo del item,
     * @return Item por Id
     ****************************************/
    @GetMapping("get/attribute-item-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAttributesItemById(@PathVariable Long id) {

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

    /***************************************
     * @param attributesItemInput Objeto a guardar
     * @return Guardar atributo de un item
     ****************************************/

    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveAttributesItem(@Valid String attributesItemInput
            , @ModelAttribute AttributesItem attributesItemDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        String message;
        int cod;
        boolean isUpdate = true;
        RequestAttributesItem attributesItem = new ObjectMapper().readValue(attributesItemInput, RequestAttributesItem.class);
        AttributesItem attributesItemNew;
        AttributesItem attributesItemIn;
        Item item;

        attributesItemIn = attributesItem.getAttributesItem();
        item = attributesItem.getItem();

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

            if (iItemServices.findByIdItem(item.getId()) != null) {

                if (iAttributesItemsServices.findByIdAttributesItem(attributesItem.getAttributesItem().getId()) != null) {
                    cod = HttpStatus.OK.value();
                    message = "El atributo del item " + attributesItem.getItem().getId() + " ha sido actualizado con exito";
                    attributesItem.getAttributesItem().setUpdateDate(new Date());
                } else {
                    isUpdate = false;
                    attributesItemIn.setId(0L);
                    cod = HttpStatus.CREATED.value();
                    message = "El atributo del item " + attributesItem.getItem().getId() + " se ha creado con exito";
                    attributesItem.getAttributesItem().setCreateDate(new Date());
                }

                attributesItemNew = iAttributesItemsServices.saveAttributesItem(attributesItemIn);
                item.setAttributesItem(attributesItemNew);
                iItemServices.saveItem(item);

            } else {
                attributesItemNew = null;
                message = "El producto con el ID: " + item.getId() + " No existe en la base de datos";
                cod = HttpStatus.NOT_FOUND.value();
            }

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", message);
        response.put("cod", cod);
        response.put("attributesItem", attributesItemNew);

        if (isUpdate) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

    }
}