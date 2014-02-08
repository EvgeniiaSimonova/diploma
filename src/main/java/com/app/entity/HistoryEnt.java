package com.app.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
@SequenceGenerator(name = "HISTORY_SEQ", sequenceName = "history_sequence", allocationSize = 1, initialValue = 1)
public class HistoryEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORY_SEQ")
    private Long id;

    @Column(name = "price_id")
    private Long priceId;

    @Column(name = "pharmacy_id")
    private Long pharmacyId;

    @Column(name = "drug_id")
    private Long drugId;

    @Column(name = "price")
    private Double price;

    @Column(name = "date")
    private Date date;

    @Column(name = "operation_type")
    private String operationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
