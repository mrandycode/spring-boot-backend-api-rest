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
    @Query("update Item itm set itm.status = ?2 where itm.id = ?1")
    void updateStatusItemById(Long term, String status);

    @Query("select itm from Item itm where itm.id in :ids")
    List<Item> findItemsByListIds(@Param("ids") List<Long> ids);

    @Procedure("SP_ITEM_DISCOUNT_INVENTORY_FROM_INVOICEDETAIL")
    String discountInventoryFromInvoicedetail(String model);

    @Procedure("SP_ITEM_ADD_INVENTORY_FROM_INVOICEDETAIL")
    String addInventoryFromInvoicedetail(String model);

    @Query("select itm from Item itm where upper(itm.name) " +
            "like concat('%', upper(:#{#item.name}), '%') " +
            "or upper(itm.nameMedia) like concat('%', upper(:#{#item.nameMedia}), '%')" +
            "or upper (itm.description) like concat('%', upper(:#{#item.description}), '%') ")
    List<Item> findItemsByWords(@Param("item") Item item);

    @Procedure("SP_UPDATE_PRICES")
    String updatePrices();

}