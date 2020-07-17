package com.yo.minimal.rest.models.iDao;

import com.yo.minimal.rest.models.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IItemDao extends PagingAndSortingRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query("update Item itm set itm.status = 'C' where itm.id = term")
    void deleteItemById(Long term, String status);

    @Query("select itm from Item itm where itm.id in :ids")
        List<Item> findItemsByListIds(@Param("ids") List<Long> ids);

    @Procedure("SP_ITEM_DISCOUNT_INVENTORY_FROM_INVOICEDETAIL")
    String discountInventoryFromInvoicedetail(String model);

    @Procedure("SP_ITEM_ADD_INVENTORY_FROM_INVOICEDETAIL")
    String addtInventoryFromInvoicedetail(String model);
}