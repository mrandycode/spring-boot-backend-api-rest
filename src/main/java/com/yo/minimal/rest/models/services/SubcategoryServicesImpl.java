package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.SubCategory;
import com.yo.minimal.rest.models.iDao.ISubcategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubcategoryServicesImpl implements ISubcategoryServices {

    @Autowired
    private ISubcategoryDao iSubcategoryDao;

    @Override
    @Transactional(readOnly = true)
    public List<SubCategory> findAllSubcategory() {
        return (List<SubCategory>) iSubcategoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategory findByIdSubcategory(Long id) {
        return iSubcategoryDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public SubCategory saveSubcategory(SubCategory subCategory) { return iSubcategoryDao.save(subCategory); }

//    @Override
//    @Transactional
//    public void updateStatusClassById(Long term, String status) {  iClassDao.deleteClassById(term, status);}

}
