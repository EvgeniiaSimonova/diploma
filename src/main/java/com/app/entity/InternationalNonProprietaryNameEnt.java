package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "international_non_proprietary_name")
public class InternationalNonProprietaryNameEnt {
    @Id
    @Column(name = "recommended_name", nullable = false)
    private String recommendedName;

    @Column(name = "synonym", nullable = true)
    private String synonym;

    @Column(name = "group", nullable = true)
    private String group;

    @OneToMany(mappedBy = "internationalNonProprietaryName", fetch = FetchType.EAGER)
    private Set<ProbablyInternationalNonProprietaryNameEnt> probablyInternationalNonProprietaryName;

    @OneToMany(mappedBy = "internationalNonProprietaryName", fetch = FetchType.EAGER)
    private Set<DrugEnt> drugs;

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

    public Set<ProbablyInternationalNonProprietaryNameEnt> getProbablyInternationalNonProprietaryName() {
        return probablyInternationalNonProprietaryName;
    }

    public void setProbablyInternationalNonProprietaryName(Set<ProbablyInternationalNonProprietaryNameEnt> probablyInternationalNonProprietaryName) {
        this.probablyInternationalNonProprietaryName = probablyInternationalNonProprietaryName;
    }

    public Set<DrugEnt> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<DrugEnt> drugs) {
        this.drugs = drugs;
    }
}
