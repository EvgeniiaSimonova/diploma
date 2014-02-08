package com.app.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sale")
@SequenceGenerator(name = "SALE_SEQ", sequenceName = "sale_sequence", allocationSize = 1, initialValue = 1)
public class SaleEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALE_SEQ")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id")
    private PharmacyEnt pharmacy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_id")
    private DrugEnt drug;

    @Column(name = "count")
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PharmacyEnt getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyEnt pharmacy) {
        this.pharmacy = pharmacy;
    }

    public DrugEnt getDrug() {
        return drug;
    }

    public void setDrug(DrugEnt drug) {
        this.drug = drug;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
