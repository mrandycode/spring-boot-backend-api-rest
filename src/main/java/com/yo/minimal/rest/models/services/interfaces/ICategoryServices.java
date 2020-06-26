package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.Category;

import java.util.List;


public interface ICategoryServices {

    public List<Category> findAllCategory();

    public Category findByIdCategory(Long id);

    public Category saveCategory(Category category);

//    public void updateStatusClassById(Long term, String status);

}
