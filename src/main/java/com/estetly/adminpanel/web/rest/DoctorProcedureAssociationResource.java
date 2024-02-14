package com.estetly.adminpanel.web.rest;

import com.estetly.adminpanel.domain.DoctorProcedureAssociation;
import com.estetly.adminpanel.repository.DoctorProcedureAssociationRepository;
import com.estetly.adminpanel.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.estetly.adminpanel.domain.DoctorProcedureAssociation}.
 */
@RestController
@RequestMapping("/api/doctor-procedure-associations")
@Transactional
public class DoctorProcedureAssociationResource {

    private final Logger log = LoggerFactory.getLogger(DoctorProcedureAssociationResource.class);

    private static final String ENTITY_NAME = "doctorProcedureAssociation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorProcedureAssociationRepository doctorProcedureAssociationRepository;

    public DoctorProcedureAssociationResource(DoctorProcedureAssociationRepository doctorProcedureAssociationRepository) {
        this.doctorProcedureAssociationRepository = doctorProcedureAssociationRepository;
    }

    /**
     * {@code POST  /doctor-procedure-associations} : Create a new doctorProcedureAssociation.
     *
     * @param doctorProcedureAssociation the doctorProcedureAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorProcedureAssociation, or with status {@code 400 (Bad Request)} if the doctorProcedureAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DoctorProcedureAssociation> createDoctorProcedureAssociation(
        @Valid @RequestBody DoctorProcedureAssociation doctorProcedureAssociation
    ) throws URISyntaxException {
        log.debug("REST request to save DoctorProcedureAssociation : {}", doctorProcedureAssociation);
        if (doctorProcedureAssociation.getId() != null) {
            throw new BadRequestAlertException("A new doctorProcedureAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorProcedureAssociation result = doctorProcedureAssociationRepository.save(doctorProcedureAssociation);
        return ResponseEntity
            .created(new URI("/api/doctor-procedure-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctor-procedure-associations/:id} : Updates an existing doctorProcedureAssociation.
     *
     * @param id the id of the doctorProcedureAssociation to save.
     * @param doctorProcedureAssociation the doctorProcedureAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorProcedureAssociation,
     * or with status {@code 400 (Bad Request)} if the doctorProcedureAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorProcedureAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DoctorProcedureAssociation> updateDoctorProcedureAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DoctorProcedureAssociation doctorProcedureAssociation
    ) throws URISyntaxException {
        log.debug("REST request to update DoctorProcedureAssociation : {}, {}", id, doctorProcedureAssociation);
        if (doctorProcedureAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorProcedureAssociation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorProcedureAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DoctorProcedureAssociation result = doctorProcedureAssociationRepository.save(doctorProcedureAssociation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorProcedureAssociation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /doctor-procedure-associations/:id} : Partial updates given fields of an existing doctorProcedureAssociation, field will ignore if it is null
     *
     * @param id the id of the doctorProcedureAssociation to save.
     * @param doctorProcedureAssociation the doctorProcedureAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorProcedureAssociation,
     * or with status {@code 400 (Bad Request)} if the doctorProcedureAssociation is not valid,
     * or with status {@code 404 (Not Found)} if the doctorProcedureAssociation is not found,
     * or with status {@code 500 (Internal Server Error)} if the doctorProcedureAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DoctorProcedureAssociation> partialUpdateDoctorProcedureAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DoctorProcedureAssociation doctorProcedureAssociation
    ) throws URISyntaxException {
        log.debug("REST request to partial update DoctorProcedureAssociation partially : {}, {}", id, doctorProcedureAssociation);
        if (doctorProcedureAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorProcedureAssociation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorProcedureAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DoctorProcedureAssociation> result = doctorProcedureAssociationRepository
            .findById(doctorProcedureAssociation.getId())
            .map(existingDoctorProcedureAssociation -> {
                if (doctorProcedureAssociation.getPicture() != null) {
                    existingDoctorProcedureAssociation.setPicture(doctorProcedureAssociation.getPicture());
                }
                if (doctorProcedureAssociation.getPictureContentType() != null) {
                    existingDoctorProcedureAssociation.setPictureContentType(doctorProcedureAssociation.getPictureContentType());
                }
                if (doctorProcedureAssociation.getDescription() != null) {
                    existingDoctorProcedureAssociation.setDescription(doctorProcedureAssociation.getDescription());
                }
                if (doctorProcedureAssociation.getCost() != null) {
                    existingDoctorProcedureAssociation.setCost(doctorProcedureAssociation.getCost());
                }

                return existingDoctorProcedureAssociation;
            })
            .map(doctorProcedureAssociationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorProcedureAssociation.getId().toString())
        );
    }

    /**
     * {@code GET  /doctor-procedure-associations} : get all the doctorProcedureAssociations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorProcedureAssociations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DoctorProcedureAssociation>> getAllDoctorProcedureAssociations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DoctorProcedureAssociations");
        Page<DoctorProcedureAssociation> page;
        if (eagerload) {
            page = doctorProcedureAssociationRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = doctorProcedureAssociationRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctor-procedure-associations/:id} : get the "id" doctorProcedureAssociation.
     *
     * @param id the id of the doctorProcedureAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorProcedureAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorProcedureAssociation> getDoctorProcedureAssociation(@PathVariable("id") Long id) {
        log.debug("REST request to get DoctorProcedureAssociation : {}", id);
        Optional<DoctorProcedureAssociation> doctorProcedureAssociation =
            doctorProcedureAssociationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(doctorProcedureAssociation);
    }

    /**
     * {@code DELETE  /doctor-procedure-associations/:id} : delete the "id" doctorProcedureAssociation.
     *
     * @param id the id of the doctorProcedureAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorProcedureAssociation(@PathVariable("id") Long id) {
        log.debug("REST request to delete DoctorProcedureAssociation : {}", id);
        doctorProcedureAssociationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
