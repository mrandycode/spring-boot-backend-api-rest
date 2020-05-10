package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observation;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy H:m:s")
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;

    @PrePersist
    private void prePersist() {
        createDate = new Date();
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceDetail> invoiceDetail;

    @NotNull
    @Column(name="total_invoice", columnDefinition = "double default 0.0")
    private Double totalInvoice;

    @NotNull
    @Column(name="subtotal_invoice", columnDefinition = "double default 0.0")
    private Double subTotalInvoice;

    private String user;

    @Column(name="pos_payment")
    private Double posPayment;

    @Column(name="pos_payment_id")
    @Size(max = 50)
    private String posPaymentId;

    @Column(name="cash_payment")
    private Double cashPayment;

    @Column(name="cash_international")
    private Double cashInternational;

    @Column(name="net_bank_payment")
    private Double netBankPayment;

    @Column(name="net_bank_payment_id")
    @Size(max = 50)
    private String netBankPaymentId;

    @Column(name="bank_check_payment")
    private Double bankCheckPayment;

    @Column(name="bank_check_id")
    @Size(max = 50)
    private String bankCheckId;

    @Column(name="bank_check_issuing ")
    @Size(max = 50)
    private String bankCheckissuing ;

    public static final String NamedQuery_discountInventoryFromInvoicedetail = "discountInventoryFromInvoicedetail";

    // Constructor sin argumentos
    public Invoice() {
       super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceDetail> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetail> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public Double getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(Double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public Double getSubTotalInvoice() {
        return subTotalInvoice;
    }

    public void setSubTotalInvoice(Double subTotalInvoice) {
        this.subTotalInvoice = subTotalInvoice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getPosPayment() {
        return posPayment;
    }

    public void setPosPayment(Double posPayment) {
        this.posPayment = posPayment;
    }

    public String getPosPaymentId() {
        return posPaymentId;
    }

    public void setPosPaymentId(String posPaymentId) {
        this.posPaymentId = posPaymentId;
    }

    public Double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public Double getCashInternational() {
        return cashInternational;
    }

    public void setCashInternational(Double cashInternational) {
        this.cashInternational = cashInternational;
    }

    public Double getNetBankPayment() {
        return netBankPayment;
    }

    public void setNetBankPayment(Double netBankPayment) {
        this.netBankPayment = netBankPayment;
    }

    public String getNetBankPaymentId() {
        return netBankPaymentId;
    }

    public void setNetBankPaymentId(String netBankPaymentId) {
        this.netBankPaymentId = netBankPaymentId;
    }

    public Double getBankCheckPayment() {
        return bankCheckPayment;
    }

    public void setBankCheckPayment(Double bankCheckPayment) {
        this.bankCheckPayment = bankCheckPayment;
    }

    public String getBankCheckId() {
        return bankCheckId;
    }

    public void setBankCheckId(String bankCheckId) {
        this.bankCheckId = bankCheckId;
    }

    public String getBankCheckissuing() {
        return bankCheckissuing;
    }

    public void setBankCheckissuing(String bankCheckissuing) {
        this.bankCheckissuing = bankCheckissuing;
    }
}
