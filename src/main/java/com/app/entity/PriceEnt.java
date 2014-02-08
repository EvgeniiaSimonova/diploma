package com.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "price")
@SequenceGenerator(name = "PRICE_SEQ", sequenceName = "price_sequence", allocationSize = 1, initialValue = 1)
public class PriceEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRICE_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private PharmacyEnt pharmacy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_id", nullable = false)
    private DrugEnt drug;

    @Column(name = "price", nullable = false)
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
