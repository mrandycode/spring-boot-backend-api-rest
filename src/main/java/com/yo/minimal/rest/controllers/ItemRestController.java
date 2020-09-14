package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.entity.Invoice;
import com.yo.minimal.rest.models.entity.Item;
import com.yo.minimal.rest.models.services.interfaces.IItemServices;
import com.yo.minimal.rest.models.services.interfaces.IUploadFilePhoto;
import com.yo.minimal.rest.utility.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items/")
public class ItemRestController {

    @Autowired
    private IItemServices iItemServices;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private IUploadFilePhoto iUploadFilePhoto;

    /***************************************
     * @return Listado de productos
     ****************************************/
    @GetMapping("get/item-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findItemAll() {

        List<Item> itemList;
        Map<String, Object> response = new HashMap<>();

        try {
            itemList = iItemServices.findAllItems();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (itemList == null || itemList.size() < 1) {
            response.put("message", "No hay productos existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("items", itemList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param page Número de página
     * @param size Tamaño de la lista a mostrar
     * @return Obtenemos listado de productos a través de paginación
     ****************************************/
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("get/item-pagination")
    public ResponseEntity<?> getItemsByPagination(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) throws IOException {

        Map<String, Object> response = new HashMap<>();
        Page<Item> itemList;
        long totalItems;
        long totalPages;

        try {

            Pageable pageRequest = PageRequest.of(page, size);
            itemList = iItemServices.findAll(pageRequest);
            totalItems = itemList.getTotalElements();
            totalPages = itemList.getTotalPages();

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (itemList.stream().count() < 1) {
            response.put("message", "No hay productos existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("items", itemList);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param id Id de Producto
     * @return Item por Id
     ****************************************/
    @GetMapping("get/item-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findItemById(@PathVariable Long id) {

        Item item;
        Map<String, Object> response = new HashMap<>();

        try {
            item = iItemServices.findByIdItem(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (item == null) {
            response.put("message", "El producto con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("item", item);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param inputIds Array de Ids
     * @return Items por listado de Ids
     ****************************************/
    @GetMapping("get/items-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findItemsByListIds(@Valid String inputIds) throws IOException {

        List<Long> itemsIds = Arrays.asList(new ObjectMapper().readValue(inputIds, Long[].class));
        List<Item> itemList;
        Map<String, Object> response = new HashMap<>();

        try {
            itemList = iItemServices.findItemsByListIds(itemsIds);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (itemList == null || itemList.size() < 1) {
            response.put("message", "El listado de IDs no existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());
        response.put("items", itemList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param itemPhoto Imagen del producto
     * @param itemInput Json con Objeto de Producto
     * @return Guardar Producto
     ****************************************/
    @PostMapping("post")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> saveItemById(@RequestParam(value = "file", required = false) MultipartFile itemPhoto
            , @Valid String itemInput
            , @ModelAttribute Item itemDto
            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        Item item = new ObjectMapper().readValue(itemInput, Item.class);
        Item itemNew;

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

            if (itemPhoto != null) {
                if (item.getId() != null && item.getId() > 0 && item.getImageName() != null
                        && item.getImageName().length() > 0) {
                    imageUpload.deleteFile(item.getImageName(), Constants.itemType);
                }
            }

            item.setImageName(imageUpload.uploadFile(itemPhoto, Constants.itemType));
            item.setCreateDate(new Date());
            item.setUser("acevallos");
            itemNew = iItemServices.saveItem(item);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El producto ha sido creado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("item", itemNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param imagePhoto Imagen del producto
     * @param itemInput Json con Objeto de Producto
     * @return Actualizar Producto
     ****************************************/
    @PutMapping("put")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateItem(@RequestParam(value = "file", required = false) MultipartFile imagePhoto
            , @Valid String itemInput
            , @ModelAttribute Item itemDto
            , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        Item item = new ObjectMapper().readValue(itemInput, Item.class);
        Item itemNow = iItemServices.findByIdItem(item.getId());
        Item itemUpdated;

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

            if (itemNow == null) {
                response.put("message", "No se encuentra el producto con el ID:  " + item.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (imagePhoto != null) {
                if (item.getId() != null && item.getId() > 0 && item.getImageName() != null
                        && item.getImageName().length() > 0) {
                    imageUpload.deleteFile(item.getImageName(), Constants.itemType);
                }
                item.setImageName(imageUpload.uploadFile(imagePhoto, Constants.itemType));
            }
            // TODO:"quitar";
            item.setUser("acevallos");
            itemUpdated = iItemServices.saveItem(item);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El producto " + itemUpdated.getId() + "-" + itemUpdated.getName() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.CREATED.value());
        response.put("item", itemUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param itemInput Json del Producto
     * @return Cambiar status Producto - Delete
     ****************************************/
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("change/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateItemStatusById(@Valid String itemInput
            , @ModelAttribute Item itemDto
            , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        Item item = new ObjectMapper().readValue(itemInput, Item.class);

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
            iItemServices.updateStatusItemById(item.getId(), item.getStatus());

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El producto " + item.getId() + "-" + item.getName() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.OK.value());
        response.put("item", item);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Cargar Foto Producto
     ****************************************/
    @GetMapping(value = "get/upload/{filename:.+}")
    public ResponseEntity<Resource> viewPhotoClient(@PathVariable String filename, HttpServletRequest request) throws IOException {

        /**JSON to JavaObject (Customer)**/
        Resource resource = iUploadFilePhoto.load(filename, Constants.itemType);

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

    /***************************************
     * @param
     * @return Descuenta el inventario de todos los items pertenecientes a una factura
     ****************************************/
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("put/discount/inventory/from/invoice")
    public ResponseEntity<?> discountInventoryFromInvoiceDetail(@Valid String invoiceInput
            , @ModelAttribute Invoice invoiceDto
            , BindingResult bindingResult) throws IOException {


        Map<String, Object> response = new HashMap<>();
        Invoice invoice = new ObjectMapper().readValue(invoiceInput, Invoice.class);

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

            iItemServices.discountInventoryFromInvoicedetail(invoiceInput);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Se han descontado los invetarios de la factura:  " + invoice.getId() + "-" + "  de manera exitosa");
        response.put("cod", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param term Palabra a buscar (auto-completar)
     * @return Retorna lista con resultado de la búsqueda
     ****************************************/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("get/by/words/{term}")
    public ResponseEntity<?> findByNameOrNameMediaOrDescriptionContainingIgnoreCase(@PathVariable String term) {

        Map<String, Object> response = new HashMap<>();
        List<Item> itemList;
        Item item = new Item();
        item.setName(term);
        item.setNameMedia(term);
        item.setDescription(term);
        try {
            itemList = iItemServices.findItemsByWords(item);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (itemList == null || itemList.size() < 1) {
            response.put("message", "La búsqueda no arrojo resultado");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("items", itemList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}