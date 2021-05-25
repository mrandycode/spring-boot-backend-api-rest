package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.PaymentInvoice;
import com.yo.minimal.rest.models.iDao.IPaymentInvoiceDao;
import com.yo.minimal.rest.models.services.interfaces.IPaymentInvoiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentInvoiceImpl implements IPaymentInvoiceServices {

    @Autowired
    private IPaymentInvoiceDao iPaymentInvoiceDao;

    @Override
    @Transactional()
    public PaymentInvoice savePaymentMethodInvoice(PaymentInvoice paymentInvoice) {
        return (PaymentInvoice) iPaymentInvoiceDao.save(paymentInvoice);
    }
}
