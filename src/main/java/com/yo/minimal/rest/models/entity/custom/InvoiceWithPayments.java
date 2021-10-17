package com.yo.minimal.rest.models.entity.custom;

import com.yo.minimal.rest.constants.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SqlResultSetMapping(
        name = "invoiceWithPaymentsMapping",
        entities = {
                @EntityResult(
                        entityClass = InvoiceWithPayments.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "nationality", column = "nacionality"),
                                @FieldResult(name = "identificationId", column = "identification_id"),
                                @FieldResult(name = "createDate", column = "create_date"),
                                @FieldResult(name = "cashVEF", column = "cash_vef"),
                                @FieldResult(name = "cashUSD", column = "cash_usd"),
                                @FieldResult(name = "tddVEF", column = "tdd_vef"),
                                @FieldResult(name = "tddVEFR", column = "tdd_vefr"),
                                @FieldResult(name = "tdcVEF", column = "tdc_vef"),
                                @FieldResult(name = "tdcVEFR", column = "tdc_vefr"),
                                @FieldResult(name = "p2pVEF", column = "p2p_vef"),
                                @FieldResult(name = "p2pVEFR", column = "p2p_vefr"),
                                @FieldResult(name = "c2pVEF", column = "c2p_vef"),
                                @FieldResult(name = "c2pVEFR", column = "c2p_vefr"),
                                @FieldResult(name = "totalVEF", column = "total_vef"),
                                @FieldResult(name = "totalUSD", column = "total_usd"),
                                @FieldResult(name = "observation", column = "observation"),
                                @FieldResult(name = "user", column = "user")

                        }
                )
        }
)
@NamedNativeQuery(
        name = "InvoiceWithPayments.getInvoiceWithPayments",
        query = Constants.QUERY_INVOICE_WITH_PAYMENTS,
        resultSetMapping = "invoiceWithPaymentsMapping"
)
@Entity
public class InvoiceWithPayments implements Serializable {
    @Id
    private Long id;
    private String nationality;
    private int identificationId;
    private Date createDate;
    private BigDecimal cashVEF;
    private BigDecimal cashUSD;
    private BigDecimal tddVEF;
    private String tddVEFR;
    private BigDecimal tdcVEF;
    private String tdcVEFR;
    private BigDecimal p2pVEF;
    private String p2pVEFR;
    private BigDecimal c2pVEF;
    private String c2pVEFR;
    private BigDecimal totalVEF;
    private BigDecimal totalUSD;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getCashVEF() {
        return cashVEF;
    }

    public void setCashVEF(BigDecimal cashVEF) {
        this.cashVEF = cashVEF;
    }

    public BigDecimal getCashUSD() {
        return cashUSD;
    }

    public void setCashUSD(BigDecimal cashUSD) {
        this.cashUSD = cashUSD;
    }

    public BigDecimal getTddVEF() {
        return tddVEF;
    }

    public void setTddVEF(BigDecimal tddVEF) {
        this.tddVEF = tddVEF;
    }

    public String getTddVEFR() {
        return tddVEFR;
    }

    public void setTddVEFR(String tddVEFR) {
        this.tddVEFR = tddVEFR;
    }

    public BigDecimal getTdcVEF() {
        return tdcVEF;
    }

    public void setTdcVEF(BigDecimal tdcVEF) {
        this.tdcVEF = tdcVEF;
    }

    public String getTdcVEFR() {
        return tdcVEFR;
    }

    public void setTdcVEFR(String tdcVEFR) {
        this.tdcVEFR = tdcVEFR;
    }

    public BigDecimal getP2pVEF() {
        return p2pVEF;
    }

    public void setP2pVEF(BigDecimal p2pVEF) {
        this.p2pVEF = p2pVEF;
    }

    public String getP2pVEFR() {
        return p2pVEFR;
    }

    public void setP2pVEFR(String p2pVEFR) {
        this.p2pVEFR = p2pVEFR;
    }

    public BigDecimal getC2pVEF() {
        return c2pVEF;
    }

    public void setC2pVEF(BigDecimal c2pVEF) {
        this.c2pVEF = c2pVEF;
    }

    public String getC2pVEFR() {
        return c2pVEFR;
    }

    public void setC2pVEFR(String c2pVEFR) {
        this.c2pVEFR = c2pVEFR;
    }

    public BigDecimal getTotalVEF() {
        return totalVEF;
    }

    public void setTotalVEF(BigDecimal totalVEF) {
        this.totalVEF = totalVEF;
    }

    public BigDecimal getTotalUSD() {
        return totalUSD;
    }

    public void setTotalUSD(BigDecimal totalUSD) {
        this.totalUSD = totalUSD;
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
