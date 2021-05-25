package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.PaymentInvoice;
import com.yo.minimal.rest.models.entity.PaymentMethod;
import com.yo.minimal.rest.models.services.interfaces.IPaymentInvoiceServices;
import com.yo.minimal.rest.models.services.interfaces.IPaymentMethodServices;
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

@RestController
@RequestMapping("/api/payment/method")
public class PaymentMethodController {

    @Autowired
    private IPaymentMethodServices iPaymentMethodServices;

    @Autowired
    private IPaymentInvoiceServices iPaymentInvoiceServices;


    /***************************************
     * @param
     * @return Listado de Facturas
     ****************************************/
    @GetMapping("get/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findPaymentMethodAll() {

        List<PaymentMethod> paymentMethods;
        Map<String, Object> response = new HashMap<>();

        try {
            paymentMethods = iPaymentMethodServices.findPaymentMethodAll();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (paymentMethods == null || paymentMethods.size() < 1) {
            response.put("message", "No hay métodos de pagos existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("paymentMethods", paymentMethods);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param paymentInvoiceInput Objeto con información de datos para ser guardado
     * @return Mensaje exitoso de guarda
     ****************************************/
    @PostMapping("post/invoice")
    @ResponseBody
    public ResponseEntity<?> saveSessionTimeout(@Valid String paymentInvoiceInput
            , @ModelAttribute PaymentMethod paymentInvoiceInputDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

         PaymentInvoice paymentInvoice = new ObjectMapper().readValue(paymentInvoiceInput, PaymentInvoice.class);
        PaymentInvoice paymentInvoiceNew;

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

            paymentInvoice.setUser("acevallos");
            paymentInvoiceNew = iPaymentInvoiceServices.savePaymentMethodInvoice(paymentInvoice);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "La moneda extranjera ha sido creado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("paymentInvoice", paymentInvoiceNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
