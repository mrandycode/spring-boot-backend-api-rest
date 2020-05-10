package com.yo.minimal.rest.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="attributesItem")
public class AttributesItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item_id")
    private Long itemId;

    private String color;
    private String sizeS;
    private String sizeM;
    private String sizeL;
    private String sizeXL;
    private String sizeXXL;
    private String [] size;
    private String material;
    private String otherAtt;
    private String user;
    private Date createDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSizeS() {
        return sizeS;
    }

    public void setSizeS(String sizeS) {
        this.sizeS = sizeS;
    }

    public String getSizeM() {
        return sizeM;
    }

    public void setSizeM(String sizeM) {
        this.sizeM = sizeM;
    }

    public String getSizeL() {
        return sizeL;
    }

    public void setSizeL(String sizeL) {
        this.sizeL = sizeL;
    }

    public String getSizeXL() {
        return sizeXL;
    }

    public void setSizeXL(String sizeXL) {
        this.sizeXL = sizeXL;
    }

    public String getSizeXXL() {
        return sizeXXL;
    }

    public void setSizeXXL(String sizeXXL) {
        this.sizeXXL = sizeXXL;
    }

    public String[] getSize() {
        return size;
    }

    public void setSize(String[] size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getOtherAtt() {
        return otherAtt;
    }

    public void setOtherAtt(String otherAtt) {
        this.otherAtt = otherAtt;
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
