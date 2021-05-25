package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.PaymentInvoice;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentInvoiceDao extends CrudRepository<PaymentInvoice, Long> {
}
