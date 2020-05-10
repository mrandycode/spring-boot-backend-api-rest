package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.CClass;
import com.yo.minimal.rest.models.iDao.IClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassServicesImpl implements IClassServices {

    @Autowired
    private IClassDao iClassDao;

    @Override
    @Transactional(readOnly = true)
    public List<CClass> findAllClass() {
        return (List<CClass>) iClassDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CClass findByIdClass(Long id) {
        return iClassDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public CClass saveClass(CClass aClass) { return iClassDao.save(aClass); }

//    @Override
//    @Transactional
//    public void updateStatusClassById(Long term, String status) {  iClassDao.deleteClassById(term, status);}

}
