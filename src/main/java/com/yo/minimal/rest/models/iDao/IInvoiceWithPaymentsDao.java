package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.custom.InvoiceWithPayments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IInvoiceWithPaymentsDao extends CrudRepository<InvoiceWithPayments, String> {
    List<InvoiceWithPayments> getInvoiceWithPayments();
}
