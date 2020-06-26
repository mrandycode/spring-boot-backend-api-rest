package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.Customer;
import com.yo.minimal.rest.models.iDao.ICustomerDao;
import com.yo.minimal.rest.models.services.interfaces.ICustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServicesImpl implements ICustomerServices {

    @Autowired
    private ICustomerDao iCustomerDao;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomer() {
       return (List<Customer>) iCustomerDao.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Customer findByIdCustomer(Long id) {
       return iCustomerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return iCustomerDao.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        iCustomerDao.deleteById(id);
    }

    @Override
    @Transactional (readOnly = true)
    public Customer searhCustomerByIdentificationId (String nacionality, String identification){
        return iCustomerDao.searhCustomerByIdentificationId(nacionality, identification);
    }
}