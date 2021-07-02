package com.yo.minimal.rest.models.services.impl;

import com.yo.minimal.rest.dto.CurrencyDto;
import com.yo.minimal.rest.models.entity.Item;
import com.yo.minimal.rest.models.entity.ItemPrice;
import com.yo.minimal.rest.models.iDao.IItemDao;
import com.yo.minimal.rest.models.services.interfaces.IItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    public Page<Item> findAll(Pageable pageRequest) {
        return iItemDao.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findByIdItem(Long id) {
        return iItemDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findItemsByListIds(List<Long> ids) {
        return iItemDao.findItemsByListIds(ids);
    }

    @Override
    @Transactional
    public Item saveItem(Item item) {
        return iItemDao.save(item);
    }

    @Override
    @Transactional()
    public Iterable<Item> saveItems(List<Item> items, CurrencyDto currency) {


        items.forEach(
                itm -> {
                    List<ItemPrice> itemPrices = itm.getItemPrices()
                            .stream()
                            .filter(ip ->
                                    ip.getForeignCurrency()
                                            .getCurrencyType()
                                            .equals(currency.getDefaultCurrency()[1]))
                            .collect(Collectors.toList());

                    itm.getItemPrices()
                            .forEach(
                                    ip -> {
                                        if (ip.getForeignCurrency().getCurrencyType()
                                                .equals(currency.getDefaultCurrency()[0])) {
                                            ip.setPrice(new BigDecimal
                                                    (currency.getPriceForeignDefault().intValue()
                                                            * itemPrices.get(0).getPrice().intValue()));
                                        }

                                    }

                            );

                }
        );


        return iItemDao.saveAll(items);
    }

    @Override
    @Transactional
    public void updateStatusItemById(Long term, String status) {
        iItemDao.updateStatusItemById(term, status);
    }

    @Override
    @Transactional
    public String discountInventoryFromInvoicedetail(String invoice) {
        return iItemDao.discountInventoryFromInvoicedetail(invoice);
    }

    @Override
    @Transactional
    public String addInventoryFromInvoicedetail(String invoice) {
        return iItemDao.addInventoryFromInvoicedetail(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findItemsByWords(Item item) {
        return iItemDao.findItemsByWords(item);
    }

    @Override
    @Transactional()
    public String updateItemPrices() {
        return iItemDao.updatePrices();
    }

}
