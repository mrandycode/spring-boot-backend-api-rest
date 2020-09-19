package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.AttributesItem;
import com.yo.minimal.rest.models.iDao.IAttributesItemDao;
import com.yo.minimal.rest.models.services.interfaces.IAttributesItemsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IAttributesItemServicesImpl implements IAttributesItemsServices {

    @Autowired
    private IAttributesItemDao iAttributesItemDao;

    @Override
    @Transactional(readOnly = true)
    public AttributesItem findByIdAttributesItem(Long id) {
        return iAttributesItemDao.findById(id).orElse(null);
    }


}
