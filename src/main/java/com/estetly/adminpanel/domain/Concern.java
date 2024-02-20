package com.estetly.adminpanel.domain;

import com.estetly.adminpanel.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Concern.
 */
@Entity
@Table(name = "concern")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Concern implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "title_fr", nullable = false)
    private String titleFr;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "other_names")
    private String otherNames;

    @Column(name = "other_names_fr")
    private String otherNamesFr;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "description_fr")
    private String descriptionFr;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "concerns" }, allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Concern id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Concern title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleFr() {
        return this.titleFr;
    }

    public Concern titleFr(String titleFr) {
        this.setTitleFr(titleFr);
        return this;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Concern gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getOtherNames() {
        return this.otherNames;
    }

    public Concern otherNames(String otherNames) {
        this.setOtherNames(otherNames);
        return this;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getOtherNamesFr() {
        return this.otherNamesFr;
    }

    public Concern otherNamesFr(String otherNamesFr) {
        this.setOtherNamesFr(otherNamesFr);
        return this;
    }

    public void setOtherNamesFr(String otherNamesFr) {
        this.otherNamesFr = otherNamesFr;
    }

    public String getDescription() {
        return this.description;
    }

    public Concern description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionFr() {
        return this.descriptionFr;
    }

    public Concern descriptionFr(String descriptionFr) {
        this.setDescriptionFr(descriptionFr);
        return this;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public Concern picture(byte[] picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public Concern pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Concern category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concern)) {
            return false;
        }
        return getId() != null && getId().equals(((Concern) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concern{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", titleFr='" + getTitleFr() + "'" +
            ", gender='" + getGender() + "'" +
            ", otherNames='" + getOtherNames() + "'" +
            ", otherNamesFr='" + getOtherNamesFr() + "'" +
            ", description='" + getDescription() + "'" +
            ", descriptionFr='" + getDescriptionFr() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            "}";
    }
}
