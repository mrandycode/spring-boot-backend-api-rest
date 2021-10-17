package com.yo.minimal.rest.models.entity.custom;

import com.yo.minimal.rest.constants.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@SqlResultSetMapping(
//        name = "invoiceCustomMapping",
//        entities = {
//                @EntityResult(
//                        entityClass = InvoiceCustom.class,
//                        fields = {
//                                @FieldResult(name = "id", column = "id"),
//                                @FieldResult(name = "nationality", column = "nationality"),
//                                @FieldResult(name = "identificationId", column = "identification_id"),
//                                @FieldResult(name = "qtyPurchased", column = "qty_purchased"),
//                                @FieldResult(name = "createDate", column = "create_date"),
//                                @FieldResult(name = "totalVEF", column = "total_vef"),
//                                @FieldResult(name = "totalUSD", column = "total_usd"),
//                                @FieldResult(name = "observation", column = "observation"),
//                                @FieldResult(name = "user", column = "user")
//                        }
//                )
//        }
//)

@NamedStoredProcedureQuery(name = "InvoiceCustom.getInvoices",
        procedureName = "SP_GET_INVOICES",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "type", type = String.class)
        })
//@NamedNativeQuery(
//        nInvoiceCustom.getInvoices,
//        query = Constants.SP_GET_INVOICES ,
//        resultSetMapping = "invoiceCustomMapping"
//)

@Entity
public class InvoiceCustom implements Serializable {
    @Id
    private Long id;
    private String nationality;
    private int identificationId;
    private int qtyPurchased;
    private Date createDate;
    private BigDecimal totalVef;
    private BigDecimal totalUsd;
    private String observation;
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(int identificationId) {
        this.identificationId = identificationId;
    }

    public int getQtyPurchased() {
        return qtyPurchased;
    }

    public void setQtyPurchased(int qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getTotalVEF() {
        return totalVef;
    }

    public void setTotalVEF(BigDecimal totalVEF) {
        this.totalVef = totalVEF;
    }

    public BigDecimal getTotalUSD() {
        return totalUsd;
    }

    public void setTotalUSD(BigDecimal totalUSD) {
        this.totalUsd = totalUSD;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private static final long serialVersionUID = 1L;
}
