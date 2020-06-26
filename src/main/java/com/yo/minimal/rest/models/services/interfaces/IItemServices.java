package com.yo.minimal.rest.models.services.interfaces;

import com.yo.minimal.rest.models.entity.Item;

import java.util.List;


public interface IItemServices {

    public List<Item> findAllItems();

    public Item findByIdItem(Long id);

    public Item saveItem (Item item);

    public void updateStatusItemById(Long term, String status);

    public String discountInventoryFromInvoicedetail(String invoice);

    public String addInventoryFromInvoicedetail(String invoice);
}
