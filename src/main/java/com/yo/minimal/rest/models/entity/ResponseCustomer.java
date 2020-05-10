package com.yo.minimal.rest.models.entity;

import java.util.List;

public class ResponseCustomer extends ResponseJ{

    private Customer customer;
    private List<Customer> customerList;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
