package com.estetly.adminpanel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.BodyArea;
import com.estetly.adminpanel.repository.BodyAreaRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link BodyAreaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BodyAreaResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/body-areas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BodyAreaRepository bodyAreaRepository;

    @Mock
    private BodyAreaRepository bodyAreaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBodyAreaMockMvc;

    private BodyArea bodyArea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyArea createEntity(EntityManager em) {
        BodyArea bodyArea = new BodyArea().code(DEFAULT_CODE).displayName(DEFAULT_DISPLAY_NAME);
        return bodyArea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyArea createUpdatedEntity(EntityManager em) {
        BodyArea bodyArea = new BodyArea().code(UPDATED_CODE).displayName(UPDATED_DISPLAY_NAME);
        return bodyArea;
    }

    @BeforeEach
    public void initTest() {
        bodyArea = createEntity(em);
    }

    @Test
    @Transactional
    void createBodyArea() throws Exception {
        int databaseSizeBeforeCreate = bodyAreaRepository.findAll().size();
        // Create the BodyArea
        restBodyAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyArea)))
            .andExpect(status().isCreated());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeCreate + 1);
        BodyArea testBodyArea = bodyAreaList.get(bodyAreaList.size() - 1);
        assertThat(testBodyArea.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBodyArea.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void createBodyAreaWithExistingId() throws Exception {
        // Create the BodyArea with an existing ID
        bodyArea.setId(1L);

        int databaseSizeBeforeCreate = bodyAreaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyArea)))
            .andExpect(status().isBadRequest());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyAreaRepository.findAll().size();
        // set the field null
        bodyArea.setCode(null);

        // Create the BodyArea, which fails.

        restBodyAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyArea)))
            .andExpect(status().isBadRequest());

        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bodyAreaRepository.findAll().size();
        // set the field null
        bodyArea.setDisplayName(null);

        // Create the BodyArea, which fails.

        restBodyAreaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyArea)))
            .andExpect(status().isBadRequest());

        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBodyAreas() throws Exception {
        // Initialize the database
        bodyAreaRepository.saveAndFlush(bodyArea);

        // Get all the bodyAreaList
        restBodyAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBodyAreasWithEagerRelationshipsIsEnabled() throws Exception {
        when(bodyAreaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBodyAreaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bodyAreaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBodyAreasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bodyAreaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBodyAreaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bodyAreaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBodyArea() throws Exception {
        // Initialize the database
        bodyAreaRepository.saveAndFlush(bodyArea);

        // Get the bodyArea
        restBodyAreaMockMvc
            .perform(get(ENTITY_API_URL_ID, bodyArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bodyArea.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBodyArea() throws Exception {
        // Get the bodyArea
        restBodyAreaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBodyArea() throws Exception {
        // Initialize the database
        bodyAreaRepository.saveAndFlush(bodyArea);

        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();

        // Update the bodyArea
        BodyArea updatedBodyArea = bodyAreaRepository.findById(bodyArea.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBodyArea are not directly saved in db
        em.detach(updatedBodyArea);
        updatedBodyArea.code(UPDATED_CODE).displayName(UPDATED_DISPLAY_NAME);

        restBodyAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBodyArea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBodyArea))
            )
            .andExpect(status().isOk());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
        BodyArea testBodyArea = bodyAreaList.get(bodyAreaList.size() - 1);
        assertThat(testBodyArea.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBodyArea.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBodyArea() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();
        bodyArea.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bodyArea.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBodyArea() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();
        bodyArea.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBodyArea() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();
        bodyArea.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyArea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBodyAreaWithPatch() throws Exception {
        // Initialize the database
        bodyAreaRepository.saveAndFlush(bodyArea);

        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();

        // Update the bodyArea using partial update
        BodyArea partialUpdatedBodyArea = new BodyArea();
        partialUpdatedBodyArea.setId(bodyArea.getId());

        partialUpdatedBodyArea.displayName(UPDATED_DISPLAY_NAME);

        restBodyAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyArea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyArea))
            )
            .andExpect(status().isOk());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
        BodyArea testBodyArea = bodyAreaList.get(bodyAreaList.size() - 1);
        assertThat(testBodyArea.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBodyArea.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBodyAreaWithPatch() throws Exception {
        // Initialize the database
        bodyAreaRepository.saveAndFlush(bodyArea);

        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();

        // Update the bodyArea using partial update
        BodyArea partialUpdatedBodyArea = new BodyArea();
        partialUpdatedBodyArea.setId(bodyArea.getId());

        partialUpdatedBodyArea.code(UPDATED_CODE).displayName(UPDATED_DISPLAY_NAME);

        restBodyAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyArea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyArea))
            )
            .andExpect(status().isOk());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
        BodyArea testBodyArea = bodyAreaList.get(bodyAreaList.size() - 1);
        assertThat(testBodyArea.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBodyArea.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBodyArea() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();
        bodyArea.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bodyArea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBodyArea() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();
        bodyArea.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBodyArea() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaRepository.findAll().size();
        bodyArea.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bodyArea)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyArea in the database
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBodyArea() throws Exception {
        // Initialize the database
        bodyAreaRepository.saveAndFlush(bodyArea);

        int databaseSizeBeforeDelete = bodyAreaRepository.findAll().size();

        // Delete the bodyArea
        restBodyAreaMockMvc
            .perform(delete(ENTITY_API_URL_ID, bodyArea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BodyArea> bodyAreaList = bodyAreaRepository.findAll();
        assertThat(bodyAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
