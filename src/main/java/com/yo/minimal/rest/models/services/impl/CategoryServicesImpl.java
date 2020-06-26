package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.Category;
import com.yo.minimal.rest.models.iDao.ICategoryDao;
import com.yo.minimal.rest.models.services.interfaces.ICategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServicesImpl implements ICategoryServices {

    @Autowired
    private ICategoryDao iCategoryDao;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategory() {
        return (List<Category>) iCategoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByIdCategory(Long id) {
        return iCategoryDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Category saveCategory(Category category) { return iCategoryDao.save(category); }

//    @Override
//    @Transactional
//    public void updateStatusClassById(Long term, String status) {  iClassDao.deleteClassById(term, status);}

}
