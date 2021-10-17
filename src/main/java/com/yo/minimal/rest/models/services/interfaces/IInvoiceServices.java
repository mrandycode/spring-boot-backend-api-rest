package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.Customer;
import com.yo.minimal.rest.models.entity.Invoice;
import com.yo.minimal.rest.dto.ResponseJ;

import java.io.IOException;
import java.util.List;


public interface IInvoiceServices {

    public Invoice findInvoiceByCustomerWithinAndInvoiceDetailWithinIteItem(Long id);

    public List<Invoice> findAllInvoice();

    public List<Invoice> findInvoiceByType(String type);

    public Invoice findByIdInvoice(Long id);

    public Invoice saveInvoice(Invoice invoice);

    public ResponseJ isInvoiceOrRefund (Invoice invoiceNew, Long idOriginal) throws IOException;

    public String markRefundIntoInvoice(String invoiceNew);

    public Invoice findInvoiceByIdAndCustomer(Long Id, Customer customer);

    public void updateInvoiceAsRefundProcessed (Invoice invoice);

    public Long getIdOriginal (Invoice invoice);

}
