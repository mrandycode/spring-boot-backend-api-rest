package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.entity.Customer;
import com.yo.minimal.rest.models.entity.Invoice;
import com.yo.minimal.rest.dto.ResponseJ;
import com.yo.minimal.rest.models.services.interfaces.IInvoiceServices;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://yo-minimal-web.herokuapp.com"})
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
    @GetMapping("get/invoice-all/{type}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findInvoiceAll(@PathVariable String type) {

        List<Invoice> invoiceList;
        Map<String, Object> response = new HashMap<>();

        try {
            invoiceList = iInvoiceServices.findInvoiceByType(type);
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
            invoice = iInvoiceServices.findInvoiceByCustomerWithinAndInvoiceDetailWithinIteItem(id);
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
        ResponseJ responseJ;
        Long idOriginal;

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

            // Si es devolución se trae el id de la FACTURA ORIGINAL, si es factura nueva se setea id a 0
            idOriginal = iInvoiceServices.getIdOriginal(invoice);

            //TODO:Quitar este Set
            invoice.setSubTotalInvoice(invoice.getTotalInvoice());

            invoice.setId(0L);
            invoiceNew = iInvoiceServices.saveInvoice(invoice);

            invoiceNew.setDescription(Long.toString(idOriginal));

            // Actualiza si la factura a crear tiene un crédito de cliente
            iInvoiceServices.updateInvoiceAsRefundProcessed(invoice);

            // Al crear la factura automáticamente llamamos a descontar o sumar el inventario según sea el caso
            responseJ = iInvoiceServices.isInvoiceOrRefund(invoiceNew, idOriginal);

            if (responseJ.getCod().equals(Integer.toString(HttpStatus.CREATED.value()))) {
                response.put("message", responseJ.getMessage());
                response.put("cod", HttpStatus.CREATED.value());
                response.put("invoice", invoiceNew);
            } else {
                response.put("message", responseJ.getMessage());
                response.put("cod", responseJ.getCod());
            }

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Buscar Factura/Devolución por Id y Cliente
     ****************************************/
    @PostMapping("get/by/id/and/customer")
    @ResponseBody
    public ResponseEntity<?> findInvoiceByIdAndCustomer(@Valid String invoiceInput
            , @ModelAttribute Invoice invoiceDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        Invoice invoice = new ObjectMapper().readValue(invoiceInput, Invoice.class);
        Long id = invoice.getId();
        Customer customer = invoice.getCustomer();
        Invoice invoiceReturn;

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

            invoiceReturn = iInvoiceServices.findInvoiceByIdAndCustomer(id, customer);

            if (invoiceReturn != null && invoiceReturn.getType().equals(Constants.TYPE_INVOICE_REFUND)) {
                response.put("message", "Consulta exitosa");
                response.put("cod", HttpStatus.FOUND.value());
                response.put("invoice", invoiceReturn);
            } else {
                response.put("message", "Factura no encontrada");
                response.put("cod", HttpStatus.NOT_FOUND.value());
            }

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}