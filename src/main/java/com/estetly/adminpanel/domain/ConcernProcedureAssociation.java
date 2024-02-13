package com.estetly.adminpanel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConcernProcedureAssociation.
 */
@Entity
@Table(name = "concern_procedure_association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConcernProcedureAssociation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Procedure procedure;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Concern concern;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConcernProcedureAssociation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Procedure getProcedure() {
        return this.procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public ConcernProcedureAssociation procedure(Procedure procedure) {
        this.setProcedure(procedure);
        return this;
    }

    public Concern getConcern() {
        return this.concern;
    }

    public void setConcern(Concern concern) {
        this.concern = concern;
    }

    public ConcernProcedureAssociation concern(Concern concern) {
        this.setConcern(concern);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConcernProcedureAssociation)) {
            return false;
        }
        return getId() != null && getId().equals(((ConcernProcedureAssociation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcernProcedureAssociation{" +
            "id=" + getId() +
            "}";
    }
}
