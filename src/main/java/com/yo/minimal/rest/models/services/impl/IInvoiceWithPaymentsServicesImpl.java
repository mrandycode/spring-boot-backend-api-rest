package com.yo.minimal.rest.models.services.impl;


import com.yo.minimal.rest.models.entity.custom.InvoiceCustom;
import com.yo.minimal.rest.models.entity.custom.InvoiceWithPayments;
import com.yo.minimal.rest.models.iDao.IInvoiceCustomDao;
import com.yo.minimal.rest.models.iDao.IInvoiceWithPaymentsDao;

import com.yo.minimal.rest.models.services.interfaces.IInvoiceWithPaymentsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IInvoiceWithPaymentsServicesImpl implements IInvoiceWithPaymentsServices {
    @Autowired
    private IInvoiceWithPaymentsDao invoiceWithPaymentsDao;

    @Autowired
    private IInvoiceCustomDao invoiceCustomDao;

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceWithPayments> getInvoiceWithPayments() {
        return invoiceWithPaymentsDao.getInvoiceWithPayments();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceCustom> getInvoices(String type) {
        return invoiceCustomDao.getInvoices(type);
    }

}
