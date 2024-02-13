package com.estetly.adminpanel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.Doctor;
import com.estetly.adminpanel.domain.DoctorProcedureAssociation;
import com.estetly.adminpanel.domain.Procedure;
import com.estetly.adminpanel.repository.DoctorProcedureAssociationRepository;
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
 * Integration tests for the {@link DoctorProcedureAssociationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DoctorProcedureAssociationResourceIT {

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;

    private static final String ENTITY_API_URL = "/api/doctor-procedure-associations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoctorProcedureAssociationRepository doctorProcedureAssociationRepository;

    @Mock
    private DoctorProcedureAssociationRepository doctorProcedureAssociationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorProcedureAssociationMockMvc;

    private DoctorProcedureAssociation doctorProcedureAssociation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorProcedureAssociation createEntity(EntityManager em) {
        DoctorProcedureAssociation doctorProcedureAssociation = new DoctorProcedureAssociation()
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .cost(DEFAULT_COST);
        // Add required entity
        Procedure procedure;
        if (TestUtil.findAll(em, Procedure.class).isEmpty()) {
            procedure = ProcedureResourceIT.createEntity(em);
            em.persist(procedure);
            em.flush();
        } else {
            procedure = TestUtil.findAll(em, Procedure.class).get(0);
        }
        doctorProcedureAssociation.setProcedure(procedure);
        // Add required entity
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            doctor = DoctorResourceIT.createEntity(em);
            em.persist(doctor);
            em.flush();
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        doctorProcedureAssociation.setDoctor(doctor);
        return doctorProcedureAssociation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorProcedureAssociation createUpdatedEntity(EntityManager em) {
        DoctorProcedureAssociation doctorProcedureAssociation = new DoctorProcedureAssociation()
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST);
        // Add required entity
        Procedure procedure;
        if (TestUtil.findAll(em, Procedure.class).isEmpty()) {
            procedure = ProcedureResourceIT.createUpdatedEntity(em);
            em.persist(procedure);
            em.flush();
        } else {
            procedure = TestUtil.findAll(em, Procedure.class).get(0);
        }
        doctorProcedureAssociation.setProcedure(procedure);
        // Add required entity
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            doctor = DoctorResourceIT.createUpdatedEntity(em);
            em.persist(doctor);
            em.flush();
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        doctorProcedureAssociation.setDoctor(doctor);
        return doctorProcedureAssociation;
    }

    @BeforeEach
    public void initTest() {
        doctorProcedureAssociation = createEntity(em);
    }

    @Test
    @Transactional
    void createDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeCreate = doctorProcedureAssociationRepository.findAll().size();
        // Create the DoctorProcedureAssociation
        restDoctorProcedureAssociationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isCreated());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorProcedureAssociation testDoctorProcedureAssociation = doctorProcedureAssociationList.get(
            doctorProcedureAssociationList.size() - 1
        );
        assertThat(testDoctorProcedureAssociation.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testDoctorProcedureAssociation.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testDoctorProcedureAssociation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDoctorProcedureAssociation.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    void createDoctorProcedureAssociationWithExistingId() throws Exception {
        // Create the DoctorProcedureAssociation with an existing ID
        doctorProcedureAssociation.setId(1L);

        int databaseSizeBeforeCreate = doctorProcedureAssociationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorProcedureAssociationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctorProcedureAssociations() throws Exception {
        // Initialize the database
        doctorProcedureAssociationRepository.saveAndFlush(doctorProcedureAssociation);

        // Get all the doctorProcedureAssociationList
        restDoctorProcedureAssociationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorProcedureAssociation.getId().intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDoctorProcedureAssociationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(doctorProcedureAssociationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDoctorProcedureAssociationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(doctorProcedureAssociationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDoctorProcedureAssociationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(doctorProcedureAssociationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDoctorProcedureAssociationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(doctorProcedureAssociationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDoctorProcedureAssociation() throws Exception {
        // Initialize the database
        doctorProcedureAssociationRepository.saveAndFlush(doctorProcedureAssociation);

        // Get the doctorProcedureAssociation
        restDoctorProcedureAssociationMockMvc
            .perform(get(ENTITY_API_URL_ID, doctorProcedureAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctorProcedureAssociation.getId().intValue()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64.getEncoder().encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDoctorProcedureAssociation() throws Exception {
        // Get the doctorProcedureAssociation
        restDoctorProcedureAssociationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctorProcedureAssociation() throws Exception {
        // Initialize the database
        doctorProcedureAssociationRepository.saveAndFlush(doctorProcedureAssociation);

        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();

        // Update the doctorProcedureAssociation
        DoctorProcedureAssociation updatedDoctorProcedureAssociation = doctorProcedureAssociationRepository
            .findById(doctorProcedureAssociation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDoctorProcedureAssociation are not directly saved in db
        em.detach(updatedDoctorProcedureAssociation);
        updatedDoctorProcedureAssociation
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST);

        restDoctorProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoctorProcedureAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDoctorProcedureAssociation))
            )
            .andExpect(status().isOk());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
        DoctorProcedureAssociation testDoctorProcedureAssociation = doctorProcedureAssociationList.get(
            doctorProcedureAssociationList.size() - 1
        );
        assertThat(testDoctorProcedureAssociation.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testDoctorProcedureAssociation.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testDoctorProcedureAssociation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDoctorProcedureAssociation.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    void putNonExistingDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();
        doctorProcedureAssociation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorProcedureAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();
        doctorProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();
        doctorProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorProcedureAssociationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorProcedureAssociationWithPatch() throws Exception {
        // Initialize the database
        doctorProcedureAssociationRepository.saveAndFlush(doctorProcedureAssociation);

        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();

        // Update the doctorProcedureAssociation using partial update
        DoctorProcedureAssociation partialUpdatedDoctorProcedureAssociation = new DoctorProcedureAssociation();
        partialUpdatedDoctorProcedureAssociation.setId(doctorProcedureAssociation.getId());

        partialUpdatedDoctorProcedureAssociation.description(UPDATED_DESCRIPTION);

        restDoctorProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorProcedureAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctorProcedureAssociation))
            )
            .andExpect(status().isOk());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
        DoctorProcedureAssociation testDoctorProcedureAssociation = doctorProcedureAssociationList.get(
            doctorProcedureAssociationList.size() - 1
        );
        assertThat(testDoctorProcedureAssociation.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testDoctorProcedureAssociation.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testDoctorProcedureAssociation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDoctorProcedureAssociation.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    void fullUpdateDoctorProcedureAssociationWithPatch() throws Exception {
        // Initialize the database
        doctorProcedureAssociationRepository.saveAndFlush(doctorProcedureAssociation);

        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();

        // Update the doctorProcedureAssociation using partial update
        DoctorProcedureAssociation partialUpdatedDoctorProcedureAssociation = new DoctorProcedureAssociation();
        partialUpdatedDoctorProcedureAssociation.setId(doctorProcedureAssociation.getId());

        partialUpdatedDoctorProcedureAssociation
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST);

        restDoctorProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorProcedureAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctorProcedureAssociation))
            )
            .andExpect(status().isOk());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
        DoctorProcedureAssociation testDoctorProcedureAssociation = doctorProcedureAssociationList.get(
            doctorProcedureAssociationList.size() - 1
        );
        assertThat(testDoctorProcedureAssociation.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testDoctorProcedureAssociation.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testDoctorProcedureAssociation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDoctorProcedureAssociation.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    void patchNonExistingDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();
        doctorProcedureAssociation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorProcedureAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();
        doctorProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctorProcedureAssociation() throws Exception {
        int databaseSizeBeforeUpdate = doctorProcedureAssociationRepository.findAll().size();
        doctorProcedureAssociation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorProcedureAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorProcedureAssociation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorProcedureAssociation in the database
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctorProcedureAssociation() throws Exception {
        // Initialize the database
        doctorProcedureAssociationRepository.saveAndFlush(doctorProcedureAssociation);

        int databaseSizeBeforeDelete = doctorProcedureAssociationRepository.findAll().size();

        // Delete the doctorProcedureAssociation
        restDoctorProcedureAssociationMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctorProcedureAssociation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorProcedureAssociation> doctorProcedureAssociationList = doctorProcedureAssociationRepository.findAll();
        assertThat(doctorProcedureAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
