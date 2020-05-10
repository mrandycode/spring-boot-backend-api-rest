package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.Invoice;

import java.util.List;


public interface IInvoiceServices {

    public List<Invoice> findAllInvoice();

    public Invoice findByIdInvoice(Long id);

    public Invoice saveInvoice(Invoice invoice);

}
