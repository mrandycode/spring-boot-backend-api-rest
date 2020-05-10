package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.Item;
import com.yo.minimal.rest.models.iDao.IItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServicesImpl implements IItemServices {

    @Autowired
    private IItemDao iItemDao;

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        return (List<Item>) iItemDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Item findByIdItem(Long id) {
        return iItemDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Item saveItem(Item item) { return iItemDao.save(item); }

    @Override
    @Transactional
    public void updateStatusItemById(Long term, String status) {  iItemDao.deleteItemById(term, status);}

    @Override
    @Transactional
    public String discountInventoryFromInvoicedetail(String invoice) { return iItemDao.discountInventoryFromInvoicedetail(invoice); }


}
