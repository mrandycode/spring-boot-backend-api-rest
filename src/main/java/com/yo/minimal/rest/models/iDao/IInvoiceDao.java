package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {
}