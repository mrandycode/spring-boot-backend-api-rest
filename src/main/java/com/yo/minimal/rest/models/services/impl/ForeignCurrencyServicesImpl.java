package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.ForeignCurrency;
import com.yo.minimal.rest.models.iDao.IForeignCurrencyDao;
import com.yo.minimal.rest.models.services.interfaces.IForeignCurrencyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForeignCurrencyServicesImpl implements IForeignCurrencyServices {

    @Autowired
    private IForeignCurrencyDao iForeignCurrencyDao;

    @Override
    @Transactional(readOnly = true)
    public List<ForeignCurrency> findAllForeignCurrency() {
        return (List<ForeignCurrency>) iForeignCurrencyDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ForeignCurrency findByIdForeignCurrency(Long id) {

        return iForeignCurrencyDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ForeignCurrency saveForeignCurrency(ForeignCurrency foreignCurrency) {
        return iForeignCurrencyDao.save(foreignCurrency);
    }


}
