package com.app.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "firm")
public class FirmEnt {
    @Id
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year", nullable = true)
    private Integer year;

    @Column(name = "country", nullable = true)
    private String country;

    @OneToMany(mappedBy = "firm", fetch = FetchType.EAGER)
    private Set<DrugEnt> drugs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<DrugEnt> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<DrugEnt> drugs) {
        this.drugs = drugs;
    }
}
