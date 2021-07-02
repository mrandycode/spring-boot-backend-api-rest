package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class ItemPrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foreignCurrency_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ForeignCurrency foreignCurrency;

    @NotEmpty
    private String user;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy H:m:s")
    private Date createDate;

    private Date updateDate;

    @PrePersist
    private void prePersist() {
        createDate = new Timestamp(System.currentTimeMillis());
    }

    public ItemPrice() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ForeignCurrency getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(ForeignCurrency foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    private static final long serialVersionUID = 1L;
}
