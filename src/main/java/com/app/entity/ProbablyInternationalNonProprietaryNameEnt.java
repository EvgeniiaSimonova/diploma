package com.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "probably_inn")
@SequenceGenerator(name = "PROBABLY_INN_SEQ", sequenceName = "probably_inn_sequence", allocationSize = 1, initialValue = 1)
public class ProbablyInternationalNonProprietaryNameEnt {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROBABLY_INN_SEQ")
    private Long id;

    @Column(name = "recommended_name", nullable = false)
    private String recommendedName;

    @Column(name = "synonym", nullable = true)
    private String synonym;

    @Column(name = "group", nullable = true)
    private String group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "international_non_proprietary_name_id")
    private InternationalNonProprietaryNameEnt internationalNonProprietaryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecommendedName() {
        return recommendedName;
    }

    public void setRecommendedName(String recommendedName) {
        this.recommendedName = recommendedName;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public InternationalNonProprietaryNameEnt getInternationalNonProprietaryName() {
        return internationalNonProprietaryName;
    }

    public void setInternationalNonProprietaryName(InternationalNonProprietaryNameEnt internationalNonProprietaryName) {
        this.internationalNonProprietaryName = internationalNonProprietaryName;
    }
}
