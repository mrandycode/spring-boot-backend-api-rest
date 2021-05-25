package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.PaymentMethod;

import java.util.List;

public interface IPaymentMethodServices {

    public List<PaymentMethod> findPaymentMethodAll();

}
