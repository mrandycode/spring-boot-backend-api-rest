package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.Category;
import com.yo.minimal.rest.models.entity.SubCategory;

import java.util.List;


public interface ISubcategoryServices {

    public List<SubCategory> findAllSubcategory();

    public SubCategory findByIdSubcategory(Long id);

    public SubCategory saveSubcategory(SubCategory category);

//    public void updateStatusClassById(Long term, String status);

}
