package com.estetly.adminpanel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.estetly.adminpanel.IntegrationTest;
import com.estetly.adminpanel.domain.Procedure;
import com.estetly.adminpanel.repository.ProcedureRepository;
import jakarta.persistence.EntityManager;
import java.util.Base64;
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
 * Integration tests for the {@link ProcedureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcedureResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_INVASIVENESS = 0;
    private static final Integer UPDATED_INVASIVENESS = 1;

    private static final Double DEFAULT_AVERAGE_COST = 1D;
    private static final Double UPDATED_AVERAGE_COST = 2D;

    private static final String ENTITY_API_URL = "/api/procedures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcedureMockMvc;

    private Procedure procedure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedure createEntity(EntityManager em) {
        Procedure procedure = new Procedure()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .invasiveness(DEFAULT_INVASIVENESS)
            .averageCost(DEFAULT_AVERAGE_COST);
        return procedure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedure createUpdatedEntity(EntityManager em) {
        Procedure procedure = new Procedure()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .invasiveness(UPDATED_INVASIVENESS)
            .averageCost(UPDATED_AVERAGE_COST);
        return procedure;
    }

    @BeforeEach
    public void initTest() {
        procedure = createEntity(em);
    }

    @Test
    @Transactional
    void createProcedure() throws Exception {
        int databaseSizeBeforeCreate = procedureRepository.findAll().size();
        // Create the Procedure
        restProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isCreated());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate + 1);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcedure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProcedure.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testProcedure.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testProcedure.getInvasiveness()).isEqualTo(DEFAULT_INVASIVENESS);
        assertThat(testProcedure.getAverageCost()).isEqualTo(DEFAULT_AVERAGE_COST);
    }

    @Test
    @Transactional
    void createProcedureWithExistingId() throws Exception {
        // Create the Procedure with an existing ID
        procedure.setId(1L);

        int databaseSizeBeforeCreate = procedureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureRepository.findAll().size();
        // set the field null
        procedure.setTitle(null);

        // Create the Procedure, which fails.

        restProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isBadRequest());

        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcedures() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].invasiveness").value(hasItem(DEFAULT_INVASIVENESS)))
            .andExpect(jsonPath("$.[*].averageCost").value(hasItem(DEFAULT_AVERAGE_COST.doubleValue())));
    }

    @Test
    @Transactional
    void getProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get the procedure
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL_ID, procedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procedure.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64.getEncoder().encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.invasiveness").value(DEFAULT_INVASIVENESS))
            .andExpect(jsonPath("$.averageCost").value(DEFAULT_AVERAGE_COST.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingProcedure() throws Exception {
        // Get the procedure
        restProcedureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure
        Procedure updatedProcedure = procedureRepository.findById(procedure.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProcedure are not directly saved in db
        em.detach(updatedProcedure);
        updatedProcedure
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .invasiveness(UPDATED_INVASIVENESS)
            .averageCost(UPDATED_AVERAGE_COST);

        restProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProcedure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProcedure))
            )
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcedure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcedure.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testProcedure.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testProcedure.getInvasiveness()).isEqualTo(UPDATED_INVASIVENESS);
        assertThat(testProcedure.getAverageCost()).isEqualTo(UPDATED_AVERAGE_COST);
    }

    @Test
    @Transactional
    void putNonExistingProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procedure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcedureWithPatch() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure using partial update
        Procedure partialUpdatedProcedure = new Procedure();
        partialUpdatedProcedure.setId(procedure.getId());

        partialUpdatedProcedure
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .invasiveness(UPDATED_INVASIVENESS)
            .averageCost(UPDATED_AVERAGE_COST);

        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcedure))
            )
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcedure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcedure.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testProcedure.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testProcedure.getInvasiveness()).isEqualTo(UPDATED_INVASIVENESS);
        assertThat(testProcedure.getAverageCost()).isEqualTo(UPDATED_AVERAGE_COST);
    }

    @Test
    @Transactional
    void fullUpdateProcedureWithPatch() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure using partial update
        Procedure partialUpdatedProcedure = new Procedure();
        partialUpdatedProcedure.setId(procedure.getId());

        partialUpdatedProcedure
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .invasiveness(UPDATED_INVASIVENESS)
            .averageCost(UPDATED_AVERAGE_COST);

        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcedure))
            )
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcedure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcedure.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testProcedure.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testProcedure.getInvasiveness()).isEqualTo(UPDATED_INVASIVENESS);
        assertThat(testProcedure.getAverageCost()).isEqualTo(UPDATED_AVERAGE_COST);
    }

    @Test
    @Transactional
    void patchNonExistingProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(procedure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeDelete = procedureRepository.findAll().size();

        // Delete the procedure
        restProcedureMockMvc
            .perform(delete(ENTITY_API_URL_ID, procedure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
