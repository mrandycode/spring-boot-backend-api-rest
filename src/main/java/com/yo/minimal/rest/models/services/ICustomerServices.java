package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.Customer;

import java.util.List;

public interface ICustomerServices  {

    public List<Customer> findAllCustomer();

    public Customer findByIdCustomer (Long id);

    public Customer saveCustomer (Customer customer);

    public void deleteCustomer (Long id);

    public Customer searhCustomerByIdentificationId (String nacionality, String identification);

}
