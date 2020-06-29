package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observation;

    private String description;

    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;

    @PrePersist
    private void prePersist() {
        createDate = new Timestamp(System.currentTimeMillis());
    }

    @Column(columnDefinition = "varchar(2) default 'I'")
    private String type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceDetail> invoiceDetail;

    @Column(name = "total_invoice", columnDefinition = "double default 0.0")
    private Double totalInvoice;

    @NotNull
    @Column(name = "subtotal_invoice", columnDefinition = "double default 0.0")
    private Double subTotalInvoice;

    private String user;

    @Column(name = "pos_payment")
    private Double posPayment;

    @Column(name = "pos_payment_id")
    @Size(max = 50)
    private String posPaymentId;

    @Column(name = "cash_payment")
    private Double cashPayment;

    @Column(name = "cash_international")
    private Double cashInternational;

    @Column(name = "net_bank_payment")
    private Double netBankPayment;

    @Column(name = "net_bank_payment_id")
    @Size(max = 50)
    private String netBankPaymentId;

    @Column(name = "bank_check_payment")
    private Double bankCheckPayment;

    @Column(name = "bank_check_id")
    @Size(max = 50)
    private String bankCheckId;

    @Column(name = "bank_check_issuing ")
    @Size(max = 50)
    private String bankCheckissuing;

    @Column(name = "customer_credit_amount")
    private Double customerCreditAmount;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate() {
        this.createDate = new Timestamp(System.currentTimeMillis());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getCustomerCreditAmount() {
        return customerCreditAmount;
    }

    public void setCustomerCreditAmount(Double customerCreditAmount) {
        this.customerCreditAmount = customerCreditAmount;
    }
}
