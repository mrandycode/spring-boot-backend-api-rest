package com.yo.minimal.rest.models.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.entity.Customer;
import com.yo.minimal.rest.models.entity.Invoice;
import com.yo.minimal.rest.dto.ResponseJ;
import com.yo.minimal.rest.models.entity.InvoiceDetail;
import com.yo.minimal.rest.models.iDao.IInvoiceDao;
import com.yo.minimal.rest.models.services.interfaces.IInvoiceServices;
import com.yo.minimal.rest.models.services.interfaces.IItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceServicesImpl implements IInvoiceServices {

    @Autowired
    private IInvoiceDao iInvoiceDao;

    @Autowired
    private IItemServices iItemServices;

    @Override
    @Transactional(readOnly = true)
    public Invoice findInvoiceByCustomerWithinAndInvoiceDetailWithinIteItem(Long id) {
        Invoice invoice = iInvoiceDao.findInvoiceByCustomerWithinAndInvoiceDetailWithinIteItem(id);
        Invoice invoiceRefund = new Invoice();
        Long refundId;
        String strId = "";

        if (invoice.getDescription() != null && !invoice.getDescription().equals("0")
                && invoice.getType().equals("I")) {
            if (invoice.getDescription().contains("R")) {
                int count = 0;
                for (int i = 0; i < invoice.getDescription().length(); i++) {
                    if (invoice.getDescription().substring(i, i + 1).equals("R")) {
                        count++;
                        if (count >= 2) {
                            refundId = Long.valueOf(strId);
                            invoiceRefund = iInvoiceDao.findInvoiceByCustomerWithinAndInvoiceDetailWithinIteItem(refundId);
                            break;
                        }
                    } else {
                        strId = strId.concat(invoice.getDescription().substring(i, i + 1));
                    }
                }
            }

            List<InvoiceDetail> invoiceDetailRefund = invoiceRefund.getInvoiceDetail();
            invoice.getInvoiceDetail()
                    .forEach(i -> invoiceDetailRefund.forEach(
                            r -> {
                                if (i.getItem().getId().equals(r.getItem().getId())) {
                                    i.setQtyPurchase(i.getQtyPurchase() - r.getQuantity());
                                }
                            })
                    );
        }

        return invoice;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAllInvoice() {
        return (List<Invoice>) iInvoiceDao.findAll();
    }

    @Override
    public List<Invoice> findInvoiceByType(String type) {
        return iInvoiceDao.findInvoiceByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice findByIdInvoice(Long id) {
        return iInvoiceDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        return iInvoiceDao.save(invoice);
    }

    @Override
    @Transactional
    public String markRefundIntoInvoice(String invoiceNew) {
        return iInvoiceDao.markRefundIntoInvoice(invoiceNew);
    }

    @Override
    @Transactional
    public ResponseJ isInvoiceOrRefund(Invoice invoiceNew, Long idOriginal) throws IOException {
        ResponseJ responseJ = new ResponseJ();
        String res;
        String invoiceNewStr = new ObjectMapper().writeValueAsString(invoiceNew);

        try {
            if (invoiceNew.getId() > 0) {

                if (invoiceNew.getType().equals(Constants.TYPE_INVOICE_INVOICE)) {
                    if (invoiceNew.getCustomerCreditAmount() > 0) {
                        invoiceNew.setDescription(Constants.TYPE_INVOICE_REFUND_PROCESSED +
                                String.valueOf(idOriginal));
                    }
                    res = iItemServices.discountInventoryFromInvoicedetail(invoiceNewStr);
                    responseJ = new ObjectMapper().readValue(res, ResponseJ.class);
                } else {
                    AtomicInteger counterOther = new AtomicInteger(0);
                    AtomicInteger counterDefective = new AtomicInteger(0);
                    invoiceNew.getInvoiceDetail().forEach(l -> {
                        if (l.getOtherRefund() > 0) {
                            counterOther.addAndGet(1);
                        }
                        if (l.getDefectiveRefund() > 0) {
                            counterDefective.addAndGet(1);
                        }
                    });

                    if (counterOther.get() > 0 && invoiceNew.getType().equals(Constants.TYPE_INVOICE_REFUND)) {
                        // Agregar inventario si la devolución no es defectuosa.
                        res = iItemServices.addInventoryFromInvoicedetail(invoiceNewStr);
                        responseJ = new ObjectMapper().readValue(res, ResponseJ.class);

                        if (responseJ.getCod().equals(Integer.toString(HttpStatus.CREATED.value()))) {
                            // Agrgamos marca de devolución en el campo Description de la factura.
                            res = this.markRefundIntoInvoice(invoiceNewStr);
                            responseJ = new ObjectMapper().readValue(res, ResponseJ.class);
                        }
                    } else if (counterDefective.get() > 0 && invoiceNew.getType().equals(Constants.TYPE_INVOICE_REFUND)) {
                        responseJ.setCod(String.valueOf(HttpStatus.CREATED.value()));
                        responseJ.setMessage(Constants.MSG_OK_EXECTUTE);
                    }
                }
            }
            return responseJ;

        } catch (IOException ex) {
            responseJ.setCod(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseJ.setMessage(ex.getMessage());
        }

        return responseJ;
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice findInvoiceByIdAndCustomer(Long id, Customer customer) {
        return iInvoiceDao.findInvoiceByIdAndCustomer(id, customer);
    }

    @Override
    @Transactional
    public void updateInvoiceAsRefundProcessed(Invoice invoice) {
        if (invoice.getCustomerCreditAmount() > 0) {
            iInvoiceDao.updateInvoiceAsRefundProcessed(Long.parseLong(invoice.getDescription()));
        }
    }

    @Override
    public Long getIdOriginal(Invoice invoice) {
        if (invoice.getType().equals(Constants.TYPE_INVOICE_REFUND)) {
            return invoice.getId();
        } else if (invoice.getType().equals(Constants.TYPE_INVOICE_INVOICE) && invoice.getCustomerCreditAmount() > 0) {
            return Long.parseLong(invoice.getDescription());
        } else {
            return 0L;
        }
    }
}
