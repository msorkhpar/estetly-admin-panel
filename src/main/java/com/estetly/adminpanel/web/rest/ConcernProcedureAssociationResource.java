package com.estetly.adminpanel.web.rest;

import com.estetly.adminpanel.domain.ConcernProcedureAssociation;
import com.estetly.adminpanel.repository.ConcernProcedureAssociationRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.estetly.adminpanel.domain.ConcernProcedureAssociation}.
 */
@RestController
@RequestMapping("/api/concern-procedure-associations")
@Transactional
public class ConcernProcedureAssociationResource {

    private final Logger log = LoggerFactory.getLogger(ConcernProcedureAssociationResource.class);

    private static final String ENTITY_NAME = "concernProcedureAssociation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcernProcedureAssociationRepository concernProcedureAssociationRepository;

    public ConcernProcedureAssociationResource(ConcernProcedureAssociationRepository concernProcedureAssociationRepository) {
        this.concernProcedureAssociationRepository = concernProcedureAssociationRepository;
    }

    /**
     * {@code POST  /concern-procedure-associations} : Create a new concernProcedureAssociation.
     *
     * @param concernProcedureAssociation the concernProcedureAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concernProcedureAssociation, or with status {@code 400 (Bad Request)} if the concernProcedureAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConcernProcedureAssociation> createConcernProcedureAssociation(
        @Valid @RequestBody ConcernProcedureAssociation concernProcedureAssociation
    ) throws URISyntaxException {
        log.debug("REST request to save ConcernProcedureAssociation : {}", concernProcedureAssociation);
        if (concernProcedureAssociation.getId() != null) {
            throw new BadRequestAlertException("A new concernProcedureAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcernProcedureAssociation result = concernProcedureAssociationRepository.save(concernProcedureAssociation);
        return ResponseEntity
            .created(new URI("/api/concern-procedure-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concern-procedure-associations/:id} : Updates an existing concernProcedureAssociation.
     *
     * @param id the id of the concernProcedureAssociation to save.
     * @param concernProcedureAssociation the concernProcedureAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concernProcedureAssociation,
     * or with status {@code 400 (Bad Request)} if the concernProcedureAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concernProcedureAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConcernProcedureAssociation> updateConcernProcedureAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConcernProcedureAssociation concernProcedureAssociation
    ) throws URISyntaxException {
        log.debug("REST request to update ConcernProcedureAssociation : {}, {}", id, concernProcedureAssociation);
        if (concernProcedureAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concernProcedureAssociation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concernProcedureAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConcernProcedureAssociation result = concernProcedureAssociationRepository.save(concernProcedureAssociation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concernProcedureAssociation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concern-procedure-associations/:id} : Partial updates given fields of an existing concernProcedureAssociation, field will ignore if it is null
     *
     * @param id the id of the concernProcedureAssociation to save.
     * @param concernProcedureAssociation the concernProcedureAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concernProcedureAssociation,
     * or with status {@code 400 (Bad Request)} if the concernProcedureAssociation is not valid,
     * or with status {@code 404 (Not Found)} if the concernProcedureAssociation is not found,
     * or with status {@code 500 (Internal Server Error)} if the concernProcedureAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConcernProcedureAssociation> partialUpdateConcernProcedureAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConcernProcedureAssociation concernProcedureAssociation
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConcernProcedureAssociation partially : {}, {}", id, concernProcedureAssociation);
        if (concernProcedureAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concernProcedureAssociation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concernProcedureAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConcernProcedureAssociation> result = concernProcedureAssociationRepository
            .findById(concernProcedureAssociation.getId())
            .map(concernProcedureAssociationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concernProcedureAssociation.getId().toString())
        );
    }

    /**
     * {@code GET  /concern-procedure-associations} : get all the concernProcedureAssociations.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concernProcedureAssociations in body.
     */
    @GetMapping("")
    public List<ConcernProcedureAssociation> getAllConcernProcedureAssociations(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ConcernProcedureAssociations");
        if (eagerload) {
            return concernProcedureAssociationRepository.findAllWithEagerRelationships();
        } else {
            return concernProcedureAssociationRepository.findAll();
        }
    }

    /**
     * {@code GET  /concern-procedure-associations/:id} : get the "id" concernProcedureAssociation.
     *
     * @param id the id of the concernProcedureAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concernProcedureAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConcernProcedureAssociation> getConcernProcedureAssociation(@PathVariable("id") Long id) {
        log.debug("REST request to get ConcernProcedureAssociation : {}", id);
        Optional<ConcernProcedureAssociation> concernProcedureAssociation =
            concernProcedureAssociationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(concernProcedureAssociation);
    }

    /**
     * {@code DELETE  /concern-procedure-associations/:id} : delete the "id" concernProcedureAssociation.
     *
     * @param id the id of the concernProcedureAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcernProcedureAssociation(@PathVariable("id") Long id) {
        log.debug("REST request to delete ConcernProcedureAssociation : {}", id);
        concernProcedureAssociationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
