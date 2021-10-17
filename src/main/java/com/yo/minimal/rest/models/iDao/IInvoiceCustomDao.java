package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.entity.custom.InvoiceCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IInvoiceCustomDao extends JpaRepository<InvoiceCustom, Long> {
    //    @Procedure(name = "InvoiceCustom.getInvoices")
    @Query(nativeQuery = true, value = Constants.SP_GET_INVOICES)
    List<InvoiceCustom> getInvoices(@Param("type") String type);
}
