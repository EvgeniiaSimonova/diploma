package com.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * User: Evgenia Simonova
 */
@Entity
@Table(name = "principal")
public class PrincipalEnt implements Serializable {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private byte[] password;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "principal", fetch = FetchType.EAGER)
    private Set<MarkerEnt> markers;

    @OneToMany(mappedBy = "principal", fetch = FetchType.EAGER)
    private Set<RatingEnt> ratings;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}