package com.estetly.adminpanel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.Concern;
import com.estetly.adminpanel.domain.ConcernProcedureAssociation;
import com.estetly.adminpanel.domain.Procedure;
import com.estetly.adminpanel.repository.ConcernProcedureAssociationRepository;
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
 * Integration tests for the {@link ConcernProcedureAssociationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConcernProcedureAssociationResourceIT {

    private static final String ENTITY_API_URL = "/api/concern-procedure-associations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcernProcedureAssociationRepository concernProcedureAssociationRepository;

    @Mock
    private ConcernProcedureAssociationRepository concernProcedureAssociationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcernProcedureAssociationMockMvc;

    private ConcernProcedureAssociation concernProcedureAssociation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcernProcedureAssociation createEntity(EntityManager em) {
        ConcernProcedureAssociation concernProcedureAssociation = new ConcernProcedureAssociation();
        // Add required entity
        Procedure procedure;
        if (TestUtil.findAll(em, Procedure.class).isEmpty()) {
            procedure = ProcedureResourceIT.createEntity(em);
            em.persist(procedure);
            em.flush();
        } else {
            procedure = TestUtil.findAll(em, Procedure.class).get(0);
        }
        concernProcedureAssociation.setProcedure(procedure);
        // Add required entity
        Concern concern;
        if (TestUtil.findAll(em, Concern.class).isEmpty()) {
            concern = ConcernResourceIT.createEntity(em);
            em.persist(concern);
            em.flush();
        } else {
            concern = TestUtil.findAll(em, Concern.class).get(0);
        }
        concernProcedureAssociation.setConcern(concern);
        return concernProcedureAssociation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcernProcedureAssociation createUpdatedEntity(EntityManager em) {
        ConcernProcedureAssociation concernProcedureAssociation = new ConcernProcedureAssociation();
        // Add required entity
        Procedure procedure;
        if (TestUtil.findAll(em, Procedure.class).isEmpty()) {
            procedure = ProcedureResourceIT.createUpdatedEntity(em);
            em.persist(procedure);
            em.flush();
        } else {
            procedure = TestUtil.findAll(em, Procedure.class).get(0);
        }
        concernProcedureAssociation.setProcedure(procedure);
        // Add required entity
        Concern concern;
        if (TestUtil.findAll(em, Concern.class).isEmpty()) {
            concern = ConcernResourceIT.createUpdatedEntity(em);
            em.persist(concern);
            em.flush();
        } else {
            concern = TestUtil.findAll(em, Concern.class).get(0);
        }
        concernProcedureAssociation.setConcern(concern);
        return concernProcedureAssociation;
    }

    @BeforeEach
    public void initTest() {
        concernProcedureAssociation = createEntity(em);
    }

    @Test
    @Transactional
    void createConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeCreate = concernProcedureAssociationRepository.findAll().size();
        // Create the ConcernProcedureAssociation
        restConcernProcedureAssociationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isCreated());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        ConcernProcedureAssociation testConcernProcedureAssociation = concernProcedureAssociationList.get(
            concernProcedureAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void createConcernProcedureAssociationWithExistingId() throws Exception {
        // Create the ConcernProcedureAssociation with an existing ID
        concernProcedureAssociation.setId(1L);

        int databaseSizeBeforeCreate = concernProcedureAssociationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcernProcedureAssociationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcernProcedureAssociations() throws Exception {
        // Initialize the database
        concernProcedureAssociationRepository.saveAndFlush(concernProcedureAssociation);

        // Get all the concernProcedureAssociationList
        restConcernProcedureAssociationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concernProcedureAssociation.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcernProcedureAssociationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(concernProcedureAssociationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcernProcedureAssociationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(concernProcedureAssociationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConcernProcedureAssociationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(concernProcedureAssociationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConcernProcedureAssociationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(concernProcedureAssociationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getConcernProcedureAssociation() throws Exception {
        // Initialize the database
        concernProcedureAssociationRepository.saveAndFlush(concernProcedureAssociation);

        // Get the concernProcedureAssociation
        restConcernProcedureAssociationMockMvc
            .perform(get(ENTITY_API_URL_ID, concernProcedureAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concernProcedureAssociation.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingConcernProcedureAssociation() throws Exception {
        // Get the concernProcedureAssociation
        restConcernProcedureAssociationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConcernProcedureAssociation() throws Exception {
        // Initialize the database
        concernProcedureAssociationRepository.saveAndFlush(concernProcedureAssociation);

        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();

        // Update the concernProcedureAssociation
        ConcernProcedureAssociation updatedConcernProcedureAssociation = concernProcedureAssociationRepository
            .findById(concernProcedureAssociation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedConcernProcedureAssociation are not directly saved in db
        em.detach(updatedConcernProcedureAssociation);

        restConcernProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcernProcedureAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcernProcedureAssociation))
            )
            .andExpect(status().isOk());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
        ConcernProcedureAssociation testConcernProcedureAssociation = concernProcedureAssociationList.get(
            concernProcedureAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void putNonExistingConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();
        concernProcedureAssociation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concernProcedureAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();
        concernProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();
        concernProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcernProcedureAssociationWithPatch() throws Exception {
        // Initialize the database
        concernProcedureAssociationRepository.saveAndFlush(concernProcedureAssociation);

        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();

        // Update the concernProcedureAssociation using partial update
        ConcernProcedureAssociation partialUpdatedConcernProcedureAssociation = new ConcernProcedureAssociation();
        partialUpdatedConcernProcedureAssociation.setId(concernProcedureAssociation.getId());

        restConcernProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcernProcedureAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcernProcedureAssociation))
            )
            .andExpect(status().isOk());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
        ConcernProcedureAssociation testConcernProcedureAssociation = concernProcedureAssociationList.get(
            concernProcedureAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void fullUpdateConcernProcedureAssociationWithPatch() throws Exception {
        // Initialize the database
        concernProcedureAssociationRepository.saveAndFlush(concernProcedureAssociation);

        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();

        // Update the concernProcedureAssociation using partial update
        ConcernProcedureAssociation partialUpdatedConcernProcedureAssociation = new ConcernProcedureAssociation();
        partialUpdatedConcernProcedureAssociation.setId(concernProcedureAssociation.getId());

        restConcernProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcernProcedureAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcernProcedureAssociation))
            )
            .andExpect(status().isOk());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
        ConcernProcedureAssociation testConcernProcedureAssociation = concernProcedureAssociationList.get(
            concernProcedureAssociationList.size() - 1
        );
    }

    @Test
    @Transactional
    void patchNonExistingConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();
        concernProcedureAssociation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concernProcedureAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();
        concernProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcernProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = concernProcedureAssociationRepository.findAll().size();
        concernProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcernProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concernProcedureAssociation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConcernProcedureAssociation in the database
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcernProcedureAssociation() throws Exception {
        // Initialize the database
        concernProcedureAssociationRepository.saveAndFlush(concernProcedureAssociation);

        int databaseSizeBeforeDelete = concernProcedureAssociationRepository.findAll().size();

        // Delete the concernProcedureAssociation
        restConcernProcedureAssociationMockMvc
            .perform(delete(ENTITY_API_URL_ID, concernProcedureAssociation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConcernProcedureAssociation> concernProcedureAssociationList = concernProcedureAssociationRepository.findAll();
        assertThat(concernProcedureAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
