package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "invoice_detail")
@JsonIgnoreProperties(ignoreUnknown = true)
public class  InvoiceDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @Column(name = "qty_purchase", columnDefinition = "int default 0")
    private Integer qtyPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Item item;

    @Column(name = "defective_refund", columnDefinition = "int default 0")
    private Integer defectiveRefund;

    @Column(name = "other_refund", columnDefinition = "int default 0")
    private Integer otherRefund;


    // Constructor sin argumentos
    public InvoiceDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQtyPurchase() {
        return qtyPurchase;
    }

    public void setQtyPurchase(Integer qtyPurchase) {
        this.qtyPurchase = qtyPurchase;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getDefectiveRefund() {
        return defectiveRefund;
    }

    public void setDefectiveRefund(int defectiveRefund) {
        this.defectiveRefund = defectiveRefund;
    }

    public int getOtherRefund() {
        return otherRefund;
    }

    public void setOtherRefund(int otherRefund) {
        this.otherRefund = otherRefund;
    }

    public void setDefectiveRefund(Integer defectiveRefund) {
        this.defectiveRefund = defectiveRefund;
    }

    public void setOtherRefund(Integer otherRefund) {
        this.otherRefund = otherRefund;
    }

    private static final long serialVersionUID = 1L;
}