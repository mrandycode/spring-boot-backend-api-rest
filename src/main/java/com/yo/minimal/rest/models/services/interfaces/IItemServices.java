package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.dto.CurrencyDto;
import com.yo.minimal.rest.models.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IItemServices {

    List<Item> findAllItems();

    Page<Item> findAll(Pageable pageRequest);

    Item findByIdItem(Long id);

    List<Item> findItemsByListIds(List<Long> ids);

    Item saveItem(Item item);

    Iterable<Item> saveItems(List<Item> items, CurrencyDto currency);

    void updateStatusItemById(Long term, String status);

    String discountInventoryFromInvoicedetail(String invoice);

    String addInventoryFromInvoicedetail(String invoice);

    List<Item> findItemsByWords(Item item);

    String updateItemPrices();
}
