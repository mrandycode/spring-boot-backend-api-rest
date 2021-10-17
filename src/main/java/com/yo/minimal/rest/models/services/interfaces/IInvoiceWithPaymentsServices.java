package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.custom.InvoiceCustom;
import com.yo.minimal.rest.models.entity.custom.InvoiceWithPayments;

import java.util.List;

public interface IInvoiceWithPaymentsServices {
    List<InvoiceWithPayments> getInvoiceWithPayments();

    List<InvoiceCustom> getInvoices(String type);
}

