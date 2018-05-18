package com.mono.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mono.app.domain.enumeration.State;

/**
 * A Orden.
 */
@Entity
@Table(name = "orden")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @NotNull
    @Column(name = "total", nullable = false)
    private Double total;

    @OneToMany(mappedBy = "orden")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Garzon> garzons = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "orden_product",
               joinColumns = @JoinColumn(name="ordens_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="products_id", referencedColumnName="id"))
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public Orden state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Double getTotal() {
        return total;
    }

    public Orden total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<Garzon> getGarzons() {
        return garzons;
    }

    public Orden garzons(Set<Garzon> garzons) {
        this.garzons = garzons;
        return this;
    }

    public Orden addGarzon(Garzon garzon) {
        this.garzons.add(garzon);
        garzon.setOrden(this);
        return this;
    }

    public Orden removeGarzon(Garzon garzon) {
        this.garzons.remove(garzon);
        garzon.setOrden(null);
        return this;
    }

    public void setGarzons(Set<Garzon> garzons) {
        this.garzons = garzons;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Orden products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Orden addProduct(Product product) {
        this.products.add(product);
        product.getOrdens().add(this);
        return this;
    }

    public Orden removeProduct(Product product) {
        this.products.remove(product);
        product.getOrdens().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
        Orden orden = (Orden) o;
        if (orden.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orden.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orden{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
