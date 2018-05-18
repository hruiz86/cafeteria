package com.mono.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mono.app.domain.enumeration.BonoType;

/**
 * A Bonos.
 */
@Entity
@Table(name = "bonos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bonos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private BonoType type;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    private Garzon garzon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BonoType getType() {
        return type;
    }

    public Bonos type(BonoType type) {
        this.type = type;
        return this;
    }

    public void setType(BonoType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public Bonos amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Garzon getGarzon() {
        return garzon;
    }

    public Bonos garzon(Garzon garzon) {
        this.garzon = garzon;
        return this;
    }

    public void setGarzon(Garzon garzon) {
        this.garzon = garzon;
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
        Bonos bonos = (Bonos) o;
        if (bonos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bonos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bonos{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
