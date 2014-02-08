package com.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "marker")
@SequenceGenerator(name = "MARKER_SEQ", sequenceName = "marker_sequence", allocationSize = 1, initialValue = 1)
public class MarkerEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MARKER_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "principal_id")
    private PrincipalEnt principal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_id")
    private DrugEnt drug;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrincipalEnt getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalEnt principal) {
        this.principal = principal;
    }

    public DrugEnt getDrug() {
        return drug;
    }

    public void setDrug(DrugEnt drug) {
        this.drug = drug;
    }
}
