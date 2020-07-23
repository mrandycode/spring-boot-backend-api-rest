package com.yo.minimal.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.models.entity.CClass;
import com.yo.minimal.rest.models.entity.Category;
import com.yo.minimal.rest.models.services.interfaces.ICategoryServices;
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
@RequestMapping("/api/category/")
public class CategoryRestController {

    @Autowired
    private ICategoryServices iCategoryServices;

    /***************************************
     * @param
     * @return Listado de categoria de un producto
     ****************************************/
    @GetMapping("get/category-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAllCategory() {

        List<Category> categoryList;
        Map<String, Object> response = new HashMap<>();

        try {
            categoryList = iCategoryServices.findAllCategory();
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (categoryList == null || categoryList.size() < 1) {
            response.put("message", "No hay Categorias existentes en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("categories", categoryList);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return CClass por Id
     ****************************************/
    @GetMapping("get/category-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) {

        Category category;
        Map<String, Object> response = new HashMap<>();

        try {
            category = iCategoryServices.findByIdCategory(id);
        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (category == null) {
            response.put("message", "La Categoría con el ID: " + id.toString() + " No existe en la base de datos");
            response.put("cod", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("catergory", category);
        response.put("message", "Consulta Exitosa");
        response.put("cod", HttpStatus.FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***************************************
     * @param
     * @return Guardar Clase
     ****************************************/
    @PostMapping("post")
    @ResponseBody
    public ResponseEntity<?> saveCategoryById(   @Valid String categoryInput
                                            , @ModelAttribute Category categoryDto
                                            , BindingResult bindingResult) throws DataAccessException, TransactionSystemException, IOException {

        Map<String, Object> response = new HashMap<>();

        Category category = new ObjectMapper().readValue(categoryInput, Category.class);
        Category categoryNew;

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

            categoryNew = iCategoryServices.saveCategory(category);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("category", categoryNew);
        response.put("message", "La Categoria ha sido creada con exito");
        response.put("cod", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /***************************************
     * @param
     * @return Actualizar una Clase
     ****************************************/
    @PutMapping("put")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateCategoryById( @Valid String categoryInput
                                         , @ModelAttribute Category categoryDto
                                         , BindingResult bindingResult) throws IOException {

        Map<String, Object> response = new HashMap<>();
        Category category = new ObjectMapper().readValue(categoryInput, Category.class);
        Category categoryNow = iCategoryServices.findByIdCategory(category.getId());
        Category categoryUpdated;

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

            if (categoryNow == null) {
                response.put("message", "No se encuentra la categoria con el ID:  " + category.getId());
                response.put("cod", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            categoryNow.setNameCategory(category.getNameCategory());
            categoryNow.setDescription(category.getDescription());

            categoryUpdated = iCategoryServices.saveCategory(categoryNow);

        } catch (DataAccessException | TransactionSystemException ex) {
            response.put("message", "Error generado por la Base de Datos -  Ex: " + ex.getMessage());
            response.put("cod", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Causa : " + ex.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("category", categoryNow);
        response.put("message", "La Clase " + categoryUpdated.getNameCategory() + "-" + categoryUpdated.getDescription() + " ha sido actualizado con exito");
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