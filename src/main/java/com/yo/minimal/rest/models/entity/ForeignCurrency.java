package com.yo.minimal.rest.models.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "foreign_currencies")
public class ForeignCurrency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "double default 0.0")
    private Double price;

    @NotEmpty
    @Column(columnDefinition = "varchar(3)")
    private String currencyType;

    @NotEmpty
    private int active;

    @Column(name = "default_currency")
    @NotEmpty
    private int defaultCurrency;

    @NotEmpty
    private String description;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy H:m:s")
    private Date createDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy H:m:s")
    private Date updateDate;

    private String user;

    public ForeignCurrency() {
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



    public String getCurrencyType() {
        return currencyType;
    }



    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }



    public int getActive() {
        return active;
    }



    public void setActive(int active) {
        this.active = active;
    }



    public int getDefaultCurrency() {
        return defaultCurrency;
    }



    public void setDefaultCurrency(int defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
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



    private static final long serialVersionUID = 1L;
}