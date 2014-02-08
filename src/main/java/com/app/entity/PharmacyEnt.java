package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pharmacy")
@SequenceGenerator(name = "PHARMACY_SEQ", sequenceName = "pharmacy_sequence", allocationSize = 1, initialValue = 1)
public class PharmacyEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHARMACY_SEQ")
    private Long id;

    @Column(name = "name", nullable = false)    // todo make unique name
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.EAGER)
    private Set<PriceEnt> prices;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.EAGER)
    private Set<SaleEnt> sales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PriceEnt> getPrices() {
        return prices;
    }

    public void setPrices(Set<PriceEnt> prices) {
        this.prices = prices;
    }

    public Set<SaleEnt> getSales() {
        return sales;
    }

    public void setSales(Set<SaleEnt> sales) {
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
