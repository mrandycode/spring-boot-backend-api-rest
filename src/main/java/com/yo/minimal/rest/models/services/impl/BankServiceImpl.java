package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.Bank;
import com.yo.minimal.rest.models.iDao.IBankDao;
import com.yo.minimal.rest.models.services.interfaces.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankServiceImpl implements IBankService {

    @Autowired
    private IBankDao iBankDao;

    @Override
    @Transactional(readOnly = true)
    public List<Bank> findAllBanks() {
        return (List<Bank>) iBankDao.findAll();
    }
}
