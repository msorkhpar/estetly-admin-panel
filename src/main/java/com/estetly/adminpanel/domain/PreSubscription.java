package com.estetly.adminpanel.domain;

import com.estetly.adminpanel.domain.enumeration.EmailStatus;
import com.estetly.adminpanel.domain.enumeration.Subscriber;
import com.estetly.adminpanel.domain.enumeration.SubscriberStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PreSubscription.
 */
@Entity
@Table(name = "pre_subscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subscriber_type", nullable = false)
    private Subscriber subscriberType;

    @NotNull
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_status", nullable = false)
    private EmailStatus emailStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subscriber_status", nullable = false)
    private SubscriberStatus subscriberStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PreSubscription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscriber getSubscriberType() {
        return this.subscriberType;
    }

    public PreSubscription subscriberType(Subscriber subscriberType) {
        this.setSubscriberType(subscriberType);
        return this;
    }

    public void setSubscriberType(Subscriber subscriberType) {
        this.subscriberType = subscriberType;
    }

    public String getFullname() {
        return this.fullname;
    }

    public PreSubscription fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return this.email;
    }

    public PreSubscription email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public PreSubscription phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public PreSubscription timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public EmailStatus getEmailStatus() {
        return this.emailStatus;
    }

    public PreSubscription emailStatus(EmailStatus emailStatus) {
        this.setEmailStatus(emailStatus);
        return this;
    }

    public void setEmailStatus(EmailStatus emailStatus) {
        this.emailStatus = emailStatus;
    }

    public SubscriberStatus getSubscriberStatus() {
        return this.subscriberStatus;
    }

    public PreSubscription subscriberStatus(SubscriberStatus subscriberStatus) {
        this.setSubscriberStatus(subscriberStatus);
        return this;
    }

    public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
        this.subscriberStatus = subscriberStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreSubscription)) {
            return false;
        }
        return getId() != null && getId().equals(((PreSubscription) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreSubscription{" +
            "id=" + getId() +
            ", subscriberType='" + getSubscriberType() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", emailStatus='" + getEmailStatus() + "'" +
            ", subscriberStatus='" + getSubscriberStatus() + "'" +
            "}";
    }
}
