package com.estetly.adminpanel.domain;

import com.estetly.adminpanel.domain.enumeration.Models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "name_fr", nullable = false)
    private String nameFr;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private Models model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Set<Concern> concerns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFr() {
        return this.nameFr;
    }

    public Category nameFr(String nameFr) {
        this.setNameFr(nameFr);
        return this;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public Models getModel() {
        return this.model;
    }

    public Category model(Models model) {
        this.setModel(model);
        return this;
    }

    public void setModel(Models model) {
        this.model = model;
    }

    public Set<Concern> getConcerns() {
        return this.concerns;
    }

    public void setConcerns(Set<Concern> concerns) {
        if (this.concerns != null) {
            this.concerns.forEach(i -> i.setCategory(null));
        }
        if (concerns != null) {
            concerns.forEach(i -> i.setCategory(this));
        }
        this.concerns = concerns;
    }

    public Category concerns(Set<Concern> concerns) {
        this.setConcerns(concerns);
        return this;
    }

    public Category addConcern(Concern concern) {
        this.concerns.add(concern);
        concern.setCategory(this);
        return this;
    }

    public Category removeConcern(Concern concern) {
        this.concerns.remove(concern);
        concern.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return getId() != null && getId().equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameFr='" + getNameFr() + "'" +
            ", model='" + getModel() + "'" +
            "}";
    }
}
