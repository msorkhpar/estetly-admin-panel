package com.estetly.adminpanel.web.rest;

import static com.estetly.adminpanel.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.PreSubscription;
import com.estetly.adminpanel.domain.enumeration.EmailStatus;
import com.estetly.adminpanel.domain.enumeration.Subscriber;
import com.estetly.adminpanel.domain.enumeration.SubscriberStatus;
import com.estetly.adminpanel.repository.PreSubscriptionRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PreSubscriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreSubscriptionResourceIT {

    private static final Subscriber DEFAULT_SUBSCRIBER_TYPE = Subscriber.PROFESSIONAL;
    private static final Subscriber UPDATED_SUBSCRIBER_TYPE = Subscriber.PATIENT;

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final EmailStatus DEFAULT_EMAIL_STATUS = EmailStatus.SENDING;
    private static final EmailStatus UPDATED_EMAIL_STATUS = EmailStatus.SENT;

    private static final SubscriberStatus DEFAULT_SUBSCRIBER_STATUS = SubscriberStatus.INITIATED;
    private static final SubscriberStatus UPDATED_SUBSCRIBER_STATUS = SubscriberStatus.PROCESSING;

    private static final String ENTITY_API_URL = "/api/pre-subscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PreSubscriptionRepository preSubscriptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreSubscriptionMockMvc;

    private PreSubscription preSubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreSubscription createEntity(EntityManager em) {
        PreSubscription preSubscription = new PreSubscription()
            .subscriberType(DEFAULT_SUBSCRIBER_TYPE)
            .fullname(DEFAULT_FULLNAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .timestamp(DEFAULT_TIMESTAMP)
            .emailStatus(DEFAULT_EMAIL_STATUS)
            .subscriberStatus(DEFAULT_SUBSCRIBER_STATUS);
        return preSubscription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreSubscription createUpdatedEntity(EntityManager em) {
        PreSubscription preSubscription = new PreSubscription()
            .subscriberType(UPDATED_SUBSCRIBER_TYPE)
            .fullname(UPDATED_FULLNAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .timestamp(UPDATED_TIMESTAMP)
            .emailStatus(UPDATED_EMAIL_STATUS)
            .subscriberStatus(UPDATED_SUBSCRIBER_STATUS);
        return preSubscription;
    }

    @BeforeEach
    public void initTest() {
        preSubscription = createEntity(em);
    }

    @Test
    @Transactional
    void createPreSubscription() throws Exception {
        int databaseSizeBeforeCreate = preSubscriptionRepository.findAll().size();
        // Create the PreSubscription
        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isCreated());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        PreSubscription testPreSubscription = preSubscriptionList.get(preSubscriptionList.size() - 1);
        assertThat(testPreSubscription.getSubscriberType()).isEqualTo(DEFAULT_SUBSCRIBER_TYPE);
        assertThat(testPreSubscription.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testPreSubscription.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPreSubscription.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPreSubscription.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testPreSubscription.getEmailStatus()).isEqualTo(DEFAULT_EMAIL_STATUS);
        assertThat(testPreSubscription.getSubscriberStatus()).isEqualTo(DEFAULT_SUBSCRIBER_STATUS);
    }

    @Test
    @Transactional
    void createPreSubscriptionWithExistingId() throws Exception {
        // Create the PreSubscription with an existing ID
        preSubscription.setId(1L);

        int databaseSizeBeforeCreate = preSubscriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSubscriberTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setSubscriberType(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFullnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setFullname(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setEmail(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setPhoneNumber(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setTimestamp(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setEmailStatus(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscriberStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = preSubscriptionRepository.findAll().size();
        // set the field null
        preSubscription.setSubscriberStatus(null);

        // Create the PreSubscription, which fails.

        restPreSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPreSubscriptions() throws Exception {
        // Initialize the database
        preSubscriptionRepository.saveAndFlush(preSubscription);

        // Get all the preSubscriptionList
        restPreSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].subscriberType").value(hasItem(DEFAULT_SUBSCRIBER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].emailStatus").value(hasItem(DEFAULT_EMAIL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].subscriberStatus").value(hasItem(DEFAULT_SUBSCRIBER_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPreSubscription() throws Exception {
        // Initialize the database
        preSubscriptionRepository.saveAndFlush(preSubscription);

        // Get the preSubscription
        restPreSubscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, preSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preSubscription.getId().intValue()))
            .andExpect(jsonPath("$.subscriberType").value(DEFAULT_SUBSCRIBER_TYPE.toString()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.emailStatus").value(DEFAULT_EMAIL_STATUS.toString()))
            .andExpect(jsonPath("$.subscriberStatus").value(DEFAULT_SUBSCRIBER_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPreSubscription() throws Exception {
        // Get the preSubscription
        restPreSubscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPreSubscription() throws Exception {
        // Initialize the database
        preSubscriptionRepository.saveAndFlush(preSubscription);

        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();

        // Update the preSubscription
        PreSubscription updatedPreSubscription = preSubscriptionRepository.findById(preSubscription.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPreSubscription are not directly saved in db
        em.detach(updatedPreSubscription);
        updatedPreSubscription
            .subscriberType(UPDATED_SUBSCRIBER_TYPE)
            .fullname(UPDATED_FULLNAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .timestamp(UPDATED_TIMESTAMP)
            .emailStatus(UPDATED_EMAIL_STATUS)
            .subscriberStatus(UPDATED_SUBSCRIBER_STATUS);

        restPreSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreSubscription.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreSubscription))
            )
            .andExpect(status().isOk());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        PreSubscription testPreSubscription = preSubscriptionList.get(preSubscriptionList.size() - 1);
        assertThat(testPreSubscription.getSubscriberType()).isEqualTo(UPDATED_SUBSCRIBER_TYPE);
        assertThat(testPreSubscription.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testPreSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPreSubscription.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPreSubscription.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testPreSubscription.getEmailStatus()).isEqualTo(UPDATED_EMAIL_STATUS);
        assertThat(testPreSubscription.getSubscriberStatus()).isEqualTo(UPDATED_SUBSCRIBER_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPreSubscription() throws Exception {
        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();
        preSubscription.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preSubscription.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreSubscription() throws Exception {
        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();
        preSubscription.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreSubscription() throws Exception {
        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();
        preSubscription.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreSubscriptionWithPatch() throws Exception {
        // Initialize the database
        preSubscriptionRepository.saveAndFlush(preSubscription);

        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();

        // Update the preSubscription using partial update
        PreSubscription partialUpdatedPreSubscription = new PreSubscription();
        partialUpdatedPreSubscription.setId(preSubscription.getId());

        partialUpdatedPreSubscription.phoneNumber(UPDATED_PHONE_NUMBER);

        restPreSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreSubscription))
            )
            .andExpect(status().isOk());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        PreSubscription testPreSubscription = preSubscriptionList.get(preSubscriptionList.size() - 1);
        assertThat(testPreSubscription.getSubscriberType()).isEqualTo(DEFAULT_SUBSCRIBER_TYPE);
        assertThat(testPreSubscription.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testPreSubscription.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPreSubscription.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPreSubscription.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testPreSubscription.getEmailStatus()).isEqualTo(DEFAULT_EMAIL_STATUS);
        assertThat(testPreSubscription.getSubscriberStatus()).isEqualTo(DEFAULT_SUBSCRIBER_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePreSubscriptionWithPatch() throws Exception {
        // Initialize the database
        preSubscriptionRepository.saveAndFlush(preSubscription);

        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();

        // Update the preSubscription using partial update
        PreSubscription partialUpdatedPreSubscription = new PreSubscription();
        partialUpdatedPreSubscription.setId(preSubscription.getId());

        partialUpdatedPreSubscription
            .subscriberType(UPDATED_SUBSCRIBER_TYPE)
            .fullname(UPDATED_FULLNAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .timestamp(UPDATED_TIMESTAMP)
            .emailStatus(UPDATED_EMAIL_STATUS)
            .subscriberStatus(UPDATED_SUBSCRIBER_STATUS);

        restPreSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreSubscription))
            )
            .andExpect(status().isOk());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        PreSubscription testPreSubscription = preSubscriptionList.get(preSubscriptionList.size() - 1);
        assertThat(testPreSubscription.getSubscriberType()).isEqualTo(UPDATED_SUBSCRIBER_TYPE);
        assertThat(testPreSubscription.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testPreSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPreSubscription.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPreSubscription.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testPreSubscription.getEmailStatus()).isEqualTo(UPDATED_EMAIL_STATUS);
        assertThat(testPreSubscription.getSubscriberStatus()).isEqualTo(UPDATED_SUBSCRIBER_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPreSubscription() throws Exception {
        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();
        preSubscription.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreSubscription() throws Exception {
        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();
        preSubscription.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreSubscription() throws Exception {
        int databaseSizeBeforeUpdate = preSubscriptionRepository.findAll().size();
        preSubscription.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preSubscription))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreSubscription in the database
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreSubscription() throws Exception {
        // Initialize the database
        preSubscriptionRepository.saveAndFlush(preSubscription);

        int databaseSizeBeforeDelete = preSubscriptionRepository.findAll().size();

        // Delete the preSubscription
        restPreSubscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, preSubscription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreSubscription> preSubscriptionList = preSubscriptionRepository.findAll();
        assertThat(preSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
