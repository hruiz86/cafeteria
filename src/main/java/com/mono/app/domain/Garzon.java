package com.mono.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Garzon.
 */
@Entity
@Table(name = "garzon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Garzon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "picture", nullable = false)
    private String picture;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Garzon userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public Garzon picture(String picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public Garzon name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public Garzon lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Garzon garzon = (Garzon) o;
        if (garzon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), garzon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Garzon{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", picture='" + getPicture() + "'" +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
