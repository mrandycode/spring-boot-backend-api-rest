package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IItemServices {

    public List<Item> findAllItems();

    public Page<Item> findAll(Pageable pageRequest);

    public Item findByIdItem(Long id);

    public List<Item> findItemsByListIds(List<Long> ids);

    public Item saveItem(Item item);

    public void updateStatusItemById(Long term, String status);

    public String discountInventoryFromInvoicedetail(String invoice);

    public String addInventoryFromInvoicedetail(String invoice);

    public List<Item> findItemsByWords(Item item);
}
