package com.estetly.adminpanel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Procedure.
 */
@Entity
@Table(name = "procedure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Procedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 254)
    @Column(name = "title", length = 254, nullable = false)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "invasiveness")
    private Integer invasiveness;

    @Column(name = "average_cost")
    private Double averageCost;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "procedure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "procedure" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Procedure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Procedure title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Procedure description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public Procedure picture(byte[] picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public Procedure pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Integer getInvasiveness() {
        return this.invasiveness;
    }

    public Procedure invasiveness(Integer invasiveness) {
        this.setInvasiveness(invasiveness);
        return this;
    }

    public void setInvasiveness(Integer invasiveness) {
        this.invasiveness = invasiveness;
    }

    public Double getAverageCost() {
        return this.averageCost;
    }

    public Procedure averageCost(Double averageCost) {
        this.setAverageCost(averageCost);
        return this;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(Set<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setProcedure(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setProcedure(this));
        }
        this.reviews = reviews;
    }

    public Procedure reviews(Set<Review> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public Procedure addReview(Review review) {
        this.reviews.add(review);
        review.setProcedure(this);
        return this;
    }

    public Procedure removeReview(Review review) {
        this.reviews.remove(review);
        review.setProcedure(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Procedure)) {
            return false;
        }
        return getId() != null && getId().equals(((Procedure) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Procedure{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", invasiveness=" + getInvasiveness() +
            ", averageCost=" + getAverageCost() +
            "}";
    }
}
