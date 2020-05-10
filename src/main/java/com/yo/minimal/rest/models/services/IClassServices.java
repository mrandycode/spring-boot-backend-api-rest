package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.CClass;

import java.util.List;


public interface IClassServices {

    public List<CClass> findAllClass();

    public CClass findByIdClass(Long id);

    public CClass saveClass(CClass aclass);

//    public void updateStatusClassById(Long term, String status);

}
