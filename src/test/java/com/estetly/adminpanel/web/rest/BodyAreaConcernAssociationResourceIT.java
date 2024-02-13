package com.estetly.adminpanel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.BodyArea;
import com.estetly.adminpanel.domain.BodyAreaConcernAssociation;
import com.estetly.adminpanel.domain.Concern;
import com.estetly.adminpanel.repository.BodyAreaConcernAssociationRepository;
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
 * Integration tests for the {@link BodyAreaConcernAssociationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BodyAreaConcernAssociationResourceIT {

    private static final String ENTITY_API_URL = "/api/body-area-concern-associations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BodyAreaConcernAssociationRepository bodyAreaConcernAssociationRepository;

    @Mock
    private BodyAreaConcernAssociationRepository bodyAreaConcernAssociationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBodyAreaConcernAssociationMockMvc;

    private BodyAreaConcernAssociation bodyAreaConcernAssociation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyAreaConcernAssociation createEntity(EntityManager em) {
        BodyAreaConcernAssociation bodyAreaConcernAssociation = new BodyAreaConcernAssociation();
        // Add required entity
        BodyArea bodyArea;
        if (TestUtil.findAll(em, BodyArea.class).isEmpty()) {
            bodyArea = BodyAreaResourceIT.createEntity(em);
            em.persist(bodyArea);
            em.flush();
        } else {
            bodyArea = TestUtil.findAll(em, BodyArea.class).get(0);
        }
        bodyAreaConcernAssociation.setBodyArea(bodyArea);
        // Add required entity
        Concern concern;
        if (TestUtil.findAll(em, Concern.class).isEmpty()) {
            concern = ConcernResourceIT.createEntity(em);
            em.persist(concern);
            em.flush();
        } else {
            concern = TestUtil.findAll(em, Concern.class).get(0);
        }
        bodyAreaConcernAssociation.setConcern(concern);
        return bodyAreaConcernAssociation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyAreaConcernAssociation createUpdatedEntity(EntityManager em) {
        BodyAreaConcernAssociation bodyAreaConcernAssociation = new BodyAreaConcernAssociation();
        // Add required entity
        BodyArea bodyArea;
        if (TestUtil.findAll(em, BodyArea.class).isEmpty()) {
            bodyArea = BodyAreaResourceIT.createUpdatedEntity(em);
            em.persist(bodyArea);
            em.flush();
        } else {
            bodyArea = TestUtil.findAll(em, BodyArea.class).get(0);
        }
        bodyAreaConcernAssociation.setBodyArea(bodyArea);
        // Add required entity
        Concern concern;
        if (TestUtil.findAll(em, Concern.class).isEmpty()) {
            concern = ConcernResourceIT.createUpdatedEntity(em);
            em.persist(concern);
            em.flush();
        } else {
            concern = TestUtil.findAll(em, Concern.class).get(0);
        }
        bodyAreaConcernAssociation.setConcern(concern);
        return bodyAreaConcernAssociation;
    }

    @BeforeEach
    public void initTest() {
        bodyAreaConcernAssociation = createEntity(em);
    }

    @Test
    @Transactional
    void createBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeCreate = bodyAreaConcernAssociationRepository.findAll().size();
        // Create the BodyAreaConcernAssociation
        restBodyAreaConcernAssociationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isCreated());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        BodyAreaConcernAssociation testBodyAreaConcernAssociation = bodyAreaConcernAssociationList.get(
            bodyAreaConcernAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void createBodyAreaConcernAssociationWithExistingId() throws Exception {
        // Create the BodyAreaConcernAssociation with an existing ID
        bodyAreaConcernAssociation.setId(1L);

        int databaseSizeBeforeCreate = bodyAreaConcernAssociationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyAreaConcernAssociationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBodyAreaConcernAssociations() throws Exception {
        // Initialize the database
        bodyAreaConcernAssociationRepository.saveAndFlush(bodyAreaConcernAssociation);

        // Get all the bodyAreaConcernAssociationList
        restBodyAreaConcernAssociationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyAreaConcernAssociation.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBodyAreaConcernAssociationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(bodyAreaConcernAssociationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBodyAreaConcernAssociationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bodyAreaConcernAssociationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBodyAreaConcernAssociationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bodyAreaConcernAssociationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBodyAreaConcernAssociationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bodyAreaConcernAssociationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBodyAreaConcernAssociation() throws Exception {
        // Initialize the database
        bodyAreaConcernAssociationRepository.saveAndFlush(bodyAreaConcernAssociation);

        // Get the bodyAreaConcernAssociation
        restBodyAreaConcernAssociationMockMvc
            .perform(get(ENTITY_API_URL_ID, bodyAreaConcernAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bodyAreaConcernAssociation.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBodyAreaConcernAssociation() throws Exception {
        // Get the bodyAreaConcernAssociation
        restBodyAreaConcernAssociationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBodyAreaConcernAssociation() throws Exception {
        // Initialize the database
        bodyAreaConcernAssociationRepository.saveAndFlush(bodyAreaConcernAssociation);

        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();

        // Update the bodyAreaConcernAssociation
        BodyAreaConcernAssociation updatedBodyAreaConcernAssociation = bodyAreaConcernAssociationRepository
            .findById(bodyAreaConcernAssociation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBodyAreaConcernAssociation are not directly saved in db
        em.detach(updatedBodyAreaConcernAssociation);

        restBodyAreaConcernAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBodyAreaConcernAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBodyAreaConcernAssociation))
            )
            .andExpect(status().isOk());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
        BodyAreaConcernAssociation testBodyAreaConcernAssociation = bodyAreaConcernAssociationList.get(
            bodyAreaConcernAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void putNonExistingBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();
        bodyAreaConcernAssociation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyAreaConcernAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bodyAreaConcernAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();
        bodyAreaConcernAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaConcernAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();
        bodyAreaConcernAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaConcernAssociationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBodyAreaConcernAssociationWithPatch() throws Exception {
        // Initialize the database
        bodyAreaConcernAssociationRepository.saveAndFlush(bodyAreaConcernAssociation);

        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();

        // Update the bodyAreaConcernAssociation using partial update
        BodyAreaConcernAssociation partialUpdatedBodyAreaConcernAssociation = new BodyAreaConcernAssociation();
        partialUpdatedBodyAreaConcernAssociation.setId(bodyAreaConcernAssociation.getId());

        restBodyAreaConcernAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyAreaConcernAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyAreaConcernAssociation))
            )
            .andExpect(status().isOk());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
        BodyAreaConcernAssociation testBodyAreaConcernAssociation = bodyAreaConcernAssociationList.get(
            bodyAreaConcernAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void fullUpdateBodyAreaConcernAssociationWithPatch() throws Exception {
        // Initialize the database
        bodyAreaConcernAssociationRepository.saveAndFlush(bodyAreaConcernAssociation);

        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();

        // Update the bodyAreaConcernAssociation using partial update
        BodyAreaConcernAssociation partialUpdatedBodyAreaConcernAssociation = new BodyAreaConcernAssociation();
        partialUpdatedBodyAreaConcernAssociation.setId(bodyAreaConcernAssociation.getId());

        restBodyAreaConcernAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyAreaConcernAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyAreaConcernAssociation))
            )
            .andExpect(status().isOk());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
        BodyAreaConcernAssociation testBodyAreaConcernAssociation = bodyAreaConcernAssociationList.get(
            bodyAreaConcernAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void patchNonExistingBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();
        bodyAreaConcernAssociation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyAreaConcernAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bodyAreaConcernAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();
        bodyAreaConcernAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaConcernAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBodyAreaConcernAssociation() throws Exception {
        int databaseSizeBeforeUpdate = bodyAreaConcernAssociationRepository.findAll().size();
        bodyAreaConcernAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyAreaConcernAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyAreaConcernAssociation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyAreaConcernAssociation in the database
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBodyAreaConcernAssociation() throws Exception {
        // Initialize the database
        bodyAreaConcernAssociationRepository.saveAndFlush(bodyAreaConcernAssociation);

        int databaseSizeBeforeDelete = bodyAreaConcernAssociationRepository.findAll().size();

        // Delete the bodyAreaConcernAssociation
        restBodyAreaConcernAssociationMockMvc
            .perform(delete(ENTITY_API_URL_ID, bodyAreaConcernAssociation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BodyAreaConcernAssociation> bodyAreaConcernAssociationList = bodyAreaConcernAssociationRepository.findAll();
        assertThat(bodyAreaConcernAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
