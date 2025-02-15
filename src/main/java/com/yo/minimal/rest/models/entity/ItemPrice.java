package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "item_price")
public class ItemPrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double price;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    private String user;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foreign_currency_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private ForeignCurrency foreignCurrency;

    // Constructor sin argumentos.
    public ItemPrice() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    

    public ForeignCurrency getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(ForeignCurrency foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }


    private static final long serialVersionUID = 1L;
}