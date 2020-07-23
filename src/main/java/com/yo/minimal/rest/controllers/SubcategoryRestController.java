package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.CClass;
import com.yo.minimal.rest.models.entity.SubCategory;
import com.yo.minimal.rest.models.services.interfaces.ISubcategoryServices;
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
@RequestMapping("/api/subcategory/")
public class SubcategoryRestController {

    @Autowired
    private ISubcategoryServices iSubcategoryServices;

    /***************************************
     * @param
     * @return Listado de categoría de un producto
     ****************************************/
    @GetMapping("get/subcategory-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAllSubcategory() {

        List<SubCategory> subCategoryList;
        Map<String, Object> response = new HashMap<>();

        try {
            subCategoryList = iSubcategoryServices.findAllSubcategory();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (subCategoryList == null || subCategoryList.size() < 1) {
            response.put("message", "No hay Subcategorías existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("subcategories", subCategoryList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return CClass por Id
     ****************************************/
    @GetMapping("get/subcategory-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findSubcategoryById(@PathVariable Long id) {

        SubCategory subCategory;
        Map<String, Object> response = new HashMap<>();

        try {
            subCategory = iSubcategoryServices.findByIdSubcategory(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (subCategory == null) {
            response.put("message", "La Subcategoría con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("subcategory", subCategory);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Guardar Subcategoría
     ****************************************/
    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveSubcategory(   @Valid String subCategoryInput
                                            , @ModelAttribute SubCategory SubCategoryDto
                                            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        SubCategory subCategory = new ObjectMapper().readValue(subCategoryInput, SubCategory.class);
        SubCategory subCategoryNew;

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

            subCategoryNew = iSubcategoryServices.saveSubcategory(subCategory);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategory", subCategoryNew);
        response.put("message", "La Subcategoría ha sido creada con exito");
        response.put("cod", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Actualizar una Subcategoría
     ****************************************/
    @PutMapping("put")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateSubcategory( @Valid String subcategoryInput
                                         , @ModelAttribute SubCategory subCategoryDto
                                         , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        SubCategory subCategory = new ObjectMapper().readValue(subcategoryInput, SubCategory.class);
        SubCategory subCategoryNow = iSubcategoryServices.findByIdSubcategory(subCategory.getId());
        SubCategory subCategoryUpdated;

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

            if (subCategoryNow == null) {
                response.put("message", "No se encuentra la Subcategoria con el ID:  " + subCategory.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            subCategoryNow.setSubCategory(subCategory.getSubCategory());
            subCategoryNow.setDescription(subCategory.getDescription());

            subCategoryUpdated = iSubcategoryServices.saveSubcategory(subCategoryNow);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategory", subCategory);
        response.put("message", "La Clase " + subCategoryUpdated.getSubCategory() + "-" + subCategoryUpdated.getDescription() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Cambiar status Clase - Delete
     ****************************************/
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("change/status")
    public ResponseEntity<?> updateItemStatusById(@Valid String classInput
            , @ModelAttribute CClass classDto
            , BindingResult bindingResult) throws IOException {


        Map<String, Object> response = new HashMap<>();
        CClass aClass = new ObjectMapper().readValue(classInput, CClass.class);

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

//            iClassServices.updateStatusClassById(aClass.getId(), aClass.getStatus());

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("class", aClass);
        response.put("message", "La Clase " + aClass.getId() + "-" + aClass.getNameClass() + " ha sido actualizado con exito");
        response.put("cod", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}