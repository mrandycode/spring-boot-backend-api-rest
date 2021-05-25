package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentMethodDao extends CrudRepository <PaymentMethod, Long> {
}
