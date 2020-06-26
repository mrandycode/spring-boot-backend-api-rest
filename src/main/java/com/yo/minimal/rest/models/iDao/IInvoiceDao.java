package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {

    @Query("select inv from Invoice inv where inv.type = ?1")
    List<Invoice> findInvoiceByType(String type);

    @Procedure("SP_MARK_REFUND_INTO_INVOICE")
    String markRefundIntoInvoice(String model);
}