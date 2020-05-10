package com.yo.minimal.rest.models.services;

import com.yo.minimal.rest.models.entity.Invoice;
import com.yo.minimal.rest.models.iDao.IInvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceServicesImpl implements IInvoiceServices {

    @Autowired
    private IInvoiceDao iInvoiceDao;

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAllInvoice() {
        return (List<Invoice>) iInvoiceDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice findByIdInvoice (Long id) {
        return iInvoiceDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Invoice saveInvoice(Invoice invoice) { return iInvoiceDao.save(invoice); }


}
