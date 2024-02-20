package com.estetly.adminpanel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.Concern;
import com.estetly.adminpanel.domain.enumeration.Gender;
import com.estetly.adminpanel.repository.ConcernRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ConcernResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConcernResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_FR = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_FR = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.FEMALE;
    private static final Gender UPDATED_GENDER = Gender.MALE;

    private static final String DEFAULT_OTHER_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_NAMES_FR = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_NAMES_FR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_FR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_FR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/concerns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcernRepository concernRepository;

    @Mock
    private ConcernRepository concernRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcernMockMvc;

    private Concern concern;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concern createEntity(EntityManager em) {
        Concern concern = new Concern()
            .title(DEFAULT_TITLE)
            .titleFr(DEFAULT_TITLE_FR)
            .gender(DEFAULT_GENDER)
            .otherNames(DEFAULT_OTHER_NAMES)
            .otherNamesFr(DEFAULT_OTHER_NAMES_FR)
            .description(DEFAULT_DESCRIPTION)
            .descriptionFr(DEFAULT_DESCRIPTION_FR)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE);
        return concern;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concern createUpdatedEntity(EntityManager em) {
        Concern concern = new Concern()
            .title(UPDATED_TITLE)
            .titleFr(UPDATED_TITLE_FR)
            .gender(UPDATED_GENDER)
            .otherNames(UPDATED_OTHER_NAMES)
            .otherNamesFr(UPDATED_OTHER_NAMES_FR)
            .description(UPDATED_DESCRIPTION)
            .descriptionFr(UPDATED_DESCRIPTION_FR)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);
        return concern;
    }

    @BeforeEach
    public void initTest() {
        concern = createEntity(em);
    }

    @Test
    @Transactional
    void createConcern() throws Exception {
        int databaseSizeBeforeCreate = concernRepository.findAll().size();
        // Create the Concern
        restConcernMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isCreated());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeCreate + 1);
        Concern testConcern = concernList.get(concernList.size() - 1);
        assertThat(testConcern.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testConcern.getTitleFr()).isEqualTo(DEFAULT_TITLE_FR);
        assertThat(testConcern.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testConcern.getOtherNames()).isEqualTo(DEFAULT_OTHER_NAMES);
        assertThat(testConcern.getOtherNamesFr()).isEqualTo(DEFAULT_OTHER_NAMES_FR);
        assertThat(testConcern.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConcern.getDescriptionFr()).isEqualTo(DEFAULT_DESCRIPTION_FR);
        assertThat(testConcern.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testConcern.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createConcernWithExistingId() throws Exception {
        // Create the Concern with an existing ID
        concern.setId(1L);

        int databaseSizeBeforeCreate = concernRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcernMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = concernRepository.findAll().size();
        // set the field null
        concern.setTitle(null);

        // Create the Concern, which fails.

        restConcernMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isBadRequest());

        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleFrIsRequired() throws Exception {
        int databaseSizeBeforeTest = concernRepository.findAll().size();
        // set the field null
        concern.setTitleFr(null);

        // Create the Concern, which fails.

        restConcernMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isBadRequest());

        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = concernRepository.findAll().size();
        // set the field null
        concern.setGender(null);

        // Create the Concern, which fails.

        restConcernMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isBadRequest());

        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConcerns() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        // Get all the concernList
        restConcernMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concern.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].titleFr").value(hasItem(DEFAULT_TITLE_FR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].otherNames").value(hasItem(DEFAULT_OTHER_NAMES)))
            .andExpect(jsonPath("$.[*].otherNamesFr").value(hasItem(DEFAULT_OTHER_NAMES_FR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].descriptionFr").value(hasItem(DEFAULT_DESCRIPTION_FR.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PICTURE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcernsWithEagerRelationshipsIsEnabled() throws Exception {
        when(concernRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcernMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(concernRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcernsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(concernRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcernMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(concernRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getConcern() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        // Get the concern
        restConcernMockMvc
            .perform(get(ENTITY_API_URL_ID, concern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concern.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.titleFr").value(DEFAULT_TITLE_FR))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.otherNames").value(DEFAULT_OTHER_NAMES))
            .andExpect(jsonPath("$.otherNamesFr").value(DEFAULT_OTHER_NAMES_FR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.descriptionFr").value(DEFAULT_DESCRIPTION_FR.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64.getEncoder().encodeToString(DEFAULT_PICTURE)));
    }

    @Test
    @Transactional
    void getNonExistingConcern() throws Exception {
        // Get the concern
        restConcernMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConcern() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        int databaseSizeBeforeUpdate = concernRepository.findAll().size();

        // Update the concern
        Concern updatedConcern = concernRepository.findById(concern.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConcern are not directly saved in db
        em.detach(updatedConcern);
        updatedConcern
            .title(UPDATED_TITLE)
            .titleFr(UPDATED_TITLE_FR)
            .gender(UPDATED_GENDER)
            .otherNames(UPDATED_OTHER_NAMES)
            .otherNamesFr(UPDATED_OTHER_NAMES_FR)
            .description(UPDATED_DESCRIPTION)
            .descriptionFr(UPDATED_DESCRIPTION_FR)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restConcernMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcern.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcern))
            )
            .andExpect(status().isOk());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
        Concern testConcern = concernList.get(concernList.size() - 1);
        assertThat(testConcern.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConcern.getTitleFr()).isEqualTo(UPDATED_TITLE_FR);
        assertThat(testConcern.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testConcern.getOtherNames()).isEqualTo(UPDATED_OTHER_NAMES);
        assertThat(testConcern.getOtherNamesFr()).isEqualTo(UPDATED_OTHER_NAMES_FR);
        assertThat(testConcern.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConcern.getDescriptionFr()).isEqualTo(UPDATED_DESCRIPTION_FR);
        assertThat(testConcern.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testConcern.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();
        concern.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concern.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concern))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();
        concern.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concern))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();
        concern.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcernWithPatch() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        int databaseSizeBeforeUpdate = concernRepository.findAll().size();

        // Update the concern using partial update
        Concern partialUpdatedConcern = new Concern();
        partialUpdatedConcern.setId(concern.getId());

        partialUpdatedConcern
            .title(UPDATED_TITLE)
            .titleFr(UPDATED_TITLE_FR)
            .otherNamesFr(UPDATED_OTHER_NAMES_FR)
            .descriptionFr(UPDATED_DESCRIPTION_FR)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restConcernMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcern.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcern))
            )
            .andExpect(status().isOk());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
        Concern testConcern = concernList.get(concernList.size() - 1);
        assertThat(testConcern.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConcern.getTitleFr()).isEqualTo(UPDATED_TITLE_FR);
        assertThat(testConcern.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testConcern.getOtherNames()).isEqualTo(DEFAULT_OTHER_NAMES);
        assertThat(testConcern.getOtherNamesFr()).isEqualTo(UPDATED_OTHER_NAMES_FR);
        assertThat(testConcern.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConcern.getDescriptionFr()).isEqualTo(UPDATED_DESCRIPTION_FR);
        assertThat(testConcern.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testConcern.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateConcernWithPatch() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        int databaseSizeBeforeUpdate = concernRepository.findAll().size();

        // Update the concern using partial update
        Concern partialUpdatedConcern = new Concern();
        partialUpdatedConcern.setId(concern.getId());

        partialUpdatedConcern
            .title(UPDATED_TITLE)
            .titleFr(UPDATED_TITLE_FR)
            .gender(UPDATED_GENDER)
            .otherNames(UPDATED_OTHER_NAMES)
            .otherNamesFr(UPDATED_OTHER_NAMES_FR)
            .description(UPDATED_DESCRIPTION)
            .descriptionFr(UPDATED_DESCRIPTION_FR)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restConcernMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcern.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcern))
            )
            .andExpect(status().isOk());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
        Concern testConcern = concernList.get(concernList.size() - 1);
        assertThat(testConcern.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConcern.getTitleFr()).isEqualTo(UPDATED_TITLE_FR);
        assertThat(testConcern.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testConcern.getOtherNames()).isEqualTo(UPDATED_OTHER_NAMES);
        assertThat(testConcern.getOtherNamesFr()).isEqualTo(UPDATED_OTHER_NAMES_FR);
        assertThat(testConcern.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConcern.getDescriptionFr()).isEqualTo(UPDATED_DESCRIPTION_FR);
        assertThat(testConcern.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testConcern.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();
        concern.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concern.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concern))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();
        concern.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concern))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();
        concern.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcern() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        int databaseSizeBeforeDelete = concernRepository.findAll().size();

        // Delete the concern
        restConcernMockMvc
            .perform(delete(ENTITY_API_URL_ID, concern.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
