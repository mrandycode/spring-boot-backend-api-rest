package com.yo.minimal.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String nameMedia;

    @NotNull
    @NotEmpty
    @Size(max = 40)
    private String description;

    private String observation;

    private String imageName;

    @NotNull
    private Double price;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    private String user;

    @NotNull
    private int idClass;

    @NotNull
    private int idCategory;

    @NotNull
    private int idSubCategory;

    @NotNull
    @NotEmpty
    @Column(columnDefinition = "char (1) default 'A'")
    private String status;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int quantity;

    @Column(name="qty_purchase", columnDefinition = "int default 0")
    private int qtyPurchase;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="attributesItem_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AttributesItem attributesItem;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ItemPrice> itemPrices;

    // Constructor sin argumentos.
    public Item() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMedia() {
        return nameMedia;
    }

    public void setNameMedia(String nameMedia) {
        this.nameMedia = nameMedia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdSubCategory() {
        return idSubCategory;
    }

    public void setIdSubCategory(int idSubCategory) {
        this.idSubCategory = idSubCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQtyPurchase() {
        return qtyPurchase;
    }

    public void setQtyPurchase(int qtyPurchase) {
        this.qtyPurchase = qtyPurchase;
    }

    public AttributesItem getAttributesItem() {
        return attributesItem;
    }

    public void setAttributesItem(AttributesItem attributesItem) {
        this.attributesItem = attributesItem;
    }

    

    public List<ItemPrice> getItemPrices() {
        return itemPrices;
    }

    public void setItemPrices(List<ItemPrice> itemPrices) {
        this.itemPrices = itemPrices;
    }



    private static final long serialVersionUID = 1L;
}