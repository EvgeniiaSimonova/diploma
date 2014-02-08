package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "drug")
@SequenceGenerator(name = "DRUG_SEQ", sequenceName = "drug_sequence", allocationSize = 1, initialValue = 1)
public class DrugEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DRUG_SEQ")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "international_non_proprietary_name_id")
    private InternationalNonProprietaryNameEnt internationalNonProprietaryName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firm_id")
    private FirmEnt firm;

    @Column(name = "dosage")
    private Double dosage;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "drug", fetch = FetchType.EAGER)
    private Set<MarkerEnt> markers;

    @OneToMany(mappedBy = "drug", fetch = FetchType.EAGER)
    private Set<RatingEnt> ratings;

    @OneToMany(mappedBy = "drug", fetch = FetchType.EAGER)
    private Set<PriceEnt> prices;

    @OneToMany(mappedBy = "drug", fetch = FetchType.EAGER)
    private Set<SaleEnt> sales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InternationalNonProprietaryNameEnt getInternationalNonProprietaryName() {
        return internationalNonProprietaryName;
    }

    public void setInternationalNonProprietaryName(InternationalNonProprietaryNameEnt internationalNonProprietaryName) {
        this.internationalNonProprietaryName = internationalNonProprietaryName;
    }

    public FirmEnt getFirm() {
        return firm;
    }

    public void setFirm(FirmEnt firm) {
        this.firm = firm;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MarkerEnt> getMarkers() {
        return markers;
    }

    public void setMarkers(Set<MarkerEnt> markers) {
        this.markers = markers;
    }

    public Set<RatingEnt> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingEnt> ratings) {
        this.ratings = ratings;
    }

    public Set<PriceEnt> getPrices() {
        return prices;
    }

    public void setPrices(Set<PriceEnt> prices) {
        this.prices = prices;
    }

    /*public Set<HistoryEnt> getHistories() {
        return histories;
    }

    public void setHistories(Set<HistoryEnt> histories) {
        this.histories = histories;
    }*/

    public Set<SaleEnt> getSales() {
        return sales;
    }

    public void setSales(Set<SaleEnt> sales) {
        this.sales = sales;
    }
}
