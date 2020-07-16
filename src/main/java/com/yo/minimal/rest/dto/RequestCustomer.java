package com.yo.minimal.rest.dto;

import com.yo.minimal.rest.models.entity.Customer;

public class RequestCustomer extends RequestJ {

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
