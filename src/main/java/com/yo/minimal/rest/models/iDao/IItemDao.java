package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface IItemDao extends CrudRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query("update Item itm set itm.status = 'C' where itm.id = term")
    void deleteItemById(Long term, String status);

    @Procedure("SP_ITEM_DISCOUNT_INVENTORY_FROM_INVOICEDETAIL")
    String discountInventoryFromInvoicedetail(String model);

    @Procedure("SP_ITEM_ADD_INVENTORY_FROM_INVOICEDETAIL")
    String addtInventoryFromInvoicedetail(String model);
}