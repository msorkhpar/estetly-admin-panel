package com.estetly.adminpanel.web.rest;

import com.estetly.adminpanel.domain.BodyAreaConcernAssociation;
import com.estetly.adminpanel.repository.BodyAreaConcernAssociationRepository;
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
 * REST controller for managing {@link com.estetly.adminpanel.domain.BodyAreaConcernAssociation}.
 */
@RestController
@RequestMapping("/api/body-area-concern-associations")
@Transactional
public class BodyAreaConcernAssociationResource {

    private final Logger log = LoggerFactory.getLogger(BodyAreaConcernAssociationResource.class);

    private static final String ENTITY_NAME = "bodyAreaConcernAssociation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyAreaConcernAssociationRepository bodyAreaConcernAssociationRepository;

    public BodyAreaConcernAssociationResource(BodyAreaConcernAssociationRepository bodyAreaConcernAssociationRepository) {
        this.bodyAreaConcernAssociationRepository = bodyAreaConcernAssociationRepository;
    }

    /**
     * {@code POST  /body-area-concern-associations} : Create a new bodyAreaConcernAssociation.
     *
     * @param bodyAreaConcernAssociation the bodyAreaConcernAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyAreaConcernAssociation, or with status {@code 400 (Bad Request)} if the bodyAreaConcernAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BodyAreaConcernAssociation> createBodyAreaConcernAssociation(
        @Valid @RequestBody BodyAreaConcernAssociation bodyAreaConcernAssociation
    ) throws URISyntaxException {
        log.debug("REST request to save BodyAreaConcernAssociation : {}", bodyAreaConcernAssociation);
        if (bodyAreaConcernAssociation.getId() != null) {
            throw new BadRequestAlertException("A new bodyAreaConcernAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyAreaConcernAssociation result = bodyAreaConcernAssociationRepository.save(bodyAreaConcernAssociation);
        return ResponseEntity
            .created(new URI("/api/body-area-concern-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /body-area-concern-associations/:id} : Updates an existing bodyAreaConcernAssociation.
     *
     * @param id the id of the bodyAreaConcernAssociation to save.
     * @param bodyAreaConcernAssociation the bodyAreaConcernAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyAreaConcernAssociation,
     * or with status {@code 400 (Bad Request)} if the bodyAreaConcernAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyAreaConcernAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BodyAreaConcernAssociation> updateBodyAreaConcernAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BodyAreaConcernAssociation bodyAreaConcernAssociation
    ) throws URISyntaxException {
        log.debug("REST request to update BodyAreaConcernAssociation : {}, {}", id, bodyAreaConcernAssociation);
        if (bodyAreaConcernAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyAreaConcernAssociation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyAreaConcernAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BodyAreaConcernAssociation result = bodyAreaConcernAssociationRepository.save(bodyAreaConcernAssociation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyAreaConcernAssociation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /body-area-concern-associations/:id} : Partial updates given fields of an existing bodyAreaConcernAssociation, field will ignore if it is null
     *
     * @param id the id of the bodyAreaConcernAssociation to save.
     * @param bodyAreaConcernAssociation the bodyAreaConcernAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyAreaConcernAssociation,
     * or with status {@code 400 (Bad Request)} if the bodyAreaConcernAssociation is not valid,
     * or with status {@code 404 (Not Found)} if the bodyAreaConcernAssociation is not found,
     * or with status {@code 500 (Internal Server Error)} if the bodyAreaConcernAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BodyAreaConcernAssociation> partialUpdateBodyAreaConcernAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BodyAreaConcernAssociation bodyAreaConcernAssociation
    ) throws URISyntaxException {
        log.debug("REST request to partial update BodyAreaConcernAssociation partially : {}, {}", id, bodyAreaConcernAssociation);
        if (bodyAreaConcernAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyAreaConcernAssociation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyAreaConcernAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BodyAreaConcernAssociation> result = bodyAreaConcernAssociationRepository
            .findById(bodyAreaConcernAssociation.getId())
            .map(bodyAreaConcernAssociationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyAreaConcernAssociation.getId().toString())
        );
    }

    /**
     * {@code GET  /body-area-concern-associations} : get all the bodyAreaConcernAssociations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodyAreaConcernAssociations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BodyAreaConcernAssociation>> getAllBodyAreaConcernAssociations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of BodyAreaConcernAssociations");
        Page<BodyAreaConcernAssociation> page;
        if (eagerload) {
            page = bodyAreaConcernAssociationRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = bodyAreaConcernAssociationRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /body-area-concern-associations/:id} : get the "id" bodyAreaConcernAssociation.
     *
     * @param id the id of the bodyAreaConcernAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyAreaConcernAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BodyAreaConcernAssociation> getBodyAreaConcernAssociation(@PathVariable("id") Long id) {
        log.debug("REST request to get BodyAreaConcernAssociation : {}", id);
        Optional<BodyAreaConcernAssociation> bodyAreaConcernAssociation =
            bodyAreaConcernAssociationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(bodyAreaConcernAssociation);
    }

    /**
     * {@code DELETE  /body-area-concern-associations/:id} : delete the "id" bodyAreaConcernAssociation.
     *
     * @param id the id of the bodyAreaConcernAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyAreaConcernAssociation(@PathVariable("id") Long id) {
        log.debug("REST request to delete BodyAreaConcernAssociation : {}", id);
        bodyAreaConcernAssociationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
