package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Customer;
import com.yo.minimal.rest.models.entity.Invoice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {

    @Query("select distinct i from Invoice i join fetch i.customer c join fetch i.invoiceDetail l join fetch l.item where i.id =?1")
    Invoice findInvoiceByCustomerWithinAndInvoiceDetailWithinIteItem(Long id);

    @Query("select distinct i from Invoice i join fetch i.customer c join fetch i.invoiceDetail l join fetch l.item where i.type =?1")
    List<Invoice> findInvoiceByType(String type);

    @Transactional
    @Modifying
    @Query("update Invoice invoice set invoice.type = 'RP' where invoice.id = ?1 and invoice.type = 'R'")
    void updateInvoiceAsRefundProcessed(Long id);

    @Procedure("SP_MARK_REFUND_INTO_INVOICE")
    String markRefundIntoInvoice(String model);

    Invoice findInvoiceByIdAndCustomer(Long id, Customer customer);

}