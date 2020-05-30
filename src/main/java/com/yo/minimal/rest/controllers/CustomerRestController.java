package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.entity.Customer;
import com.yo.minimal.rest.models.services.ICustomerServices;
import com.yo.minimal.rest.models.services.IUploadFilePhoto;
import com.yo.minimal.rest.utility.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Permite tener integridad entre servidores, en este caso FrontEnd Angular CORS
 **/
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/")
public class CustomerRestController {

    private final static String PHOTO_NO_DISP = System.getProperty("fileNameNoDisp");

    @Autowired
    private ICustomerServices iCustomerServices;

    @Autowired
    private IUploadFilePhoto iUploadFilePhoto;

    @Autowired
    private ImageUpload imageUpload;

    List<String> files = new ArrayList<String>();

    /***************************************
     * @param
     * @return Listado de clientes total
     ****************************************/
    @GetMapping("get/customer-all")
    @ResponseStatus(HttpStatus.OK)
    //public ResponseCustomer findCustomerAll () {
    public List<Customer> findCustomerAll() {
        return iCustomerServices.findAllCustomer();
    }

    /***************************************
     * @param
     * @return Listado de clientes por Id
     * En las clases de Angular, hay un modulo donde
     * se requiere enviar distintos estatus en la respues
     * se cambia a ResponseEntity (antes era Customer)
     * esta anotación es de spring.
     * ResponseEntity <?> devuelve cualquier cosa
     ****************************************/
    @GetMapping("get/customer-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {

        Customer customer;
        Map<String, Object> response = new HashMap<>();

        try {
            customer = iCustomerServices.findByIdCustomer(id);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (customer == null) {
            response.put("message", "El cliente con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (customer.getPhoto().isEmpty()) {
            customer.setPhoto(PHOTO_NO_DISP);
        }

        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Guardar Cliente
     ****************************************/
    @PostMapping("post/customer")
    // La anotacion @Valid es importante para la validaación de la clase entity con las anotaciones que ya tiene cada atributo.
    @ResponseBody
    public ResponseEntity<?> saveCustomer(@RequestParam(value = "file", required = false) MultipartFile photo
            , @Valid String customerInput
            , @ModelAttribute Customer customerDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();
        /**JSON to JavaObject (Customer)**/
        Customer customer = new ObjectMapper().readValue(customerInput, Customer.class);
        Customer customerNew;

        // De esta manera realizamos enviamos las validaciones del BackEnd al frontEnd
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
            if (photo != null) {
                if (customer.getId() != null && customer.getId() > 0 && customer.getPhoto() != null
                        && customer.getPhoto().length() > 0) {
                    imageUpload.deleteFile(customer.getPhoto(), Constants.custoType);
                }
            }

            customer.setPhoto(imageUpload.uploadFile(photo, Constants.custoType));
            customer.setCreateDate(new Date().toString());
            customerNew = iCustomerServices.saveCustomer(customer);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "El cliente ha sido creado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("customer", customerNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Eliminar Cliente
     ****************************************/
    @DeleteMapping("delete/customer-id/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            iCustomerServices.deleteCustomer(id);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El cliente ha sido eliminado con exito");
        response.put("cod", HttpStatus.OK.value());

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Actualizar Cliente
     ****************************************/
    @PutMapping("put/customer-id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateCustomer(@RequestParam(value = "file", required = false) MultipartFile photo
            , @Valid String customerInput
            , @ModelAttribute Customer customerDto
            , BindingResult bindingResult) throws IOException {

        /**JSON to JavaObject (Customer)**/
        Customer customer = new ObjectMapper().readValue(customerInput, Customer.class);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        Map<String, Object> response = new HashMap<>();
        Customer customerNow = iCustomerServices.findByIdCustomer(customer.getId());
        Customer customerUpdated;

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getFieldErrors()
                    .stream()
                    .map(err ->
                            "El campo: '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errorList);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            if (customerNow == null) {
                response.put("message", "No se encuentra el cliente con el ID:  " + customer.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            if (photo != null) {
                if (customer.getId() != null && customer.getId() > 0 && customer.getPhoto() != null
                        && customer.getPhoto().length() > 0) {
                    imageUpload.deleteFile(customer.getPhoto(), Constants.custoType);
                }
                customer.setPhoto(imageUpload.uploadFile(photo, Constants.custoType));
            }

            customerUpdated = iCustomerServices.saveCustomer(customer);

        } catch (DataAccessException | TransactionSystemException ex) {

            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "El cliente " + customerUpdated.getNacionality() + "-" + customerUpdated.getIdentificationId() + "se encuentra");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("customer", customerUpdated);

        /***El método Save sirve para insertar o Actualizar***/
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /***************************************
     * @param
     * @return Buscar Cliente por Identificación
     ****************************************/
    @PostMapping("get/customer-identification")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> searhCustomerByIdentificationId(@Valid String customerInput
            , @ModelAttribute Customer customerDto
            , BindingResult bindingResult) throws IOException {

        /**JSON to JavaObject (Customer)**/
        Customer customer = new ObjectMapper().readValue(customerInput, Customer.class);
        Customer customerNow;

        Map<String, Object> response = new HashMap<>();


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
            customerNow = iCustomerServices.searhCustomerByIdentificationId(customer.getNacionality(), customer.getIdentificationId());

        } catch (DataAccessException | TransactionSystemException ex) {

            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (customerNow == null) {
            response.put("message", "El cliente con la identificación: " + customer.getNacionality() + "-"
                    + customer.getIdentificationId() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "El cliente " + customer.getNacionality() + "-" + customer.getIdentificationId() + " se encuentra en la Base de Datos");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("customer", customerNow);

        /***El método Save sirve para insertar o Actualizar***/
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /***************************************
     * @param
     * @return Cargar Foto Cliente
     ****************************************/
    @GetMapping(value = "get/upload/{filename:.+}")
    public ResponseEntity<Resource> viewPhotoClient(@PathVariable String filename, HttpServletRequest request) throws IOException {

        /**JSON to JavaObject (Customer)**/
        Resource resource = iUploadFilePhoto.load(filename, Constants.custoType);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            System.out.println(contentType);
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile() + "\"")
                .body(resource);
    }

    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(CustomerRestController.class, "viewPhotoClient", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

}

