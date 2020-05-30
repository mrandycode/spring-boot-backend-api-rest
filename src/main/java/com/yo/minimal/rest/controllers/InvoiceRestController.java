package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.Invoice;
import com.yo.minimal.rest.models.entity.ResponseJ;
import com.yo.minimal.rest.models.services.IInvoiceServices;
import com.yo.minimal.rest.models.services.IItemServices;
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

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/invoices/")
public class InvoiceRestController {

    @Autowired
    private IInvoiceServices iInvoiceServices;

    @Autowired
    private IItemServices iItemServices;

    /***************************************
     * @param
     * @return Listado de Facturas
     ****************************************/
    @GetMapping("get/invoice-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findInvoiceAll() {

        List<Invoice> invoiceList;
        Map<String, Object> response = new HashMap<>();

        try {
            invoiceList = iInvoiceServices.findAllInvoice();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (invoiceList == null || invoiceList.size() < 1) {
            response.put("message", "No hay facturas existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("invoices", invoiceList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Factura por Id
     ****************************************/
    @GetMapping("get/invoice-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findInvoiceById(@PathVariable Long id) {

        Invoice invoice;
        Map<String, Object> response = new HashMap<>();

        try {
            invoice = iInvoiceServices.findByIdInvoice(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (invoice == null) {
            response.put("message", "La factura con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("invoice", invoice);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Guardar Factura
     ****************************************/
    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveInvoiceById(@Valid String invoiceInput
            , @ModelAttribute Invoice invoiceDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        Invoice invoice = new ObjectMapper().readValue(invoiceInput, Invoice.class);
        Invoice invoiceNew;

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

            // TODO: QUITAR CABLES
            invoice.setUser("acevallos");
            invoice.setSubTotalInvoice(invoice.getTotalInvoice());
            invoiceNew = iInvoiceServices.saveInvoice(invoice);

            // Al crear la factura automáticamente llamamos a descontar el inventario
            if (invoiceNew.getId() > 0) {
                String invoiceNewStr = new ObjectMapper().writeValueAsString(invoiceNew);
                String res = iItemServices.discountInventoryFromInvoicedetail(invoiceNewStr);
                ResponseJ responseJ = new ObjectMapper().readValue(res, ResponseJ.class);

                if (responseJ.getCod().equals(Integer.toString(HttpStatus.CREATED.value()))) {
                    response.put("message", responseJ.getMessage());
                    response.put("cod", HttpStatus.CREATED.value());
                    response.put("invoice", invoiceNew);
                } else {
                    response.put("message", responseJ.getMessage());
                    response.put("cod", responseJ.getCod());
                }
            }

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}