package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.models.entity.PaymentInvoice;
import com.yo.minimal.rest.models.entity.PaymentMethod;
import com.yo.minimal.rest.models.iDao.IPaymentMethodDao;
import com.yo.minimal.rest.models.services.interfaces.IPaymentMethodServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentMethodImpl implements IPaymentMethodServices {

    @Autowired
    private IPaymentMethodDao iPaymentMethodDao;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethod> findPaymentMethodAll() {
        return (List<PaymentMethod>) iPaymentMethodDao.findAll();
    }

}
