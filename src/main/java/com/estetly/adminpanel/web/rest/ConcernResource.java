package com.estetly.adminpanel.web.rest;

import com.estetly.adminpanel.domain.Concern;
import com.estetly.adminpanel.repository.ConcernRepository;
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
 * REST controller for managing {@link com.estetly.adminpanel.domain.Concern}.
 */
@RestController
@RequestMapping("/api/concerns")
@Transactional
public class ConcernResource {

    private final Logger log = LoggerFactory.getLogger(ConcernResource.class);

    private static final String ENTITY_NAME = "concern";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcernRepository concernRepository;

    public ConcernResource(ConcernRepository concernRepository) {
        this.concernRepository = concernRepository;
    }

    /**
     * {@code POST  /concerns} : Create a new concern.
     *
     * @param concern the concern to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concern, or with status {@code 400 (Bad Request)} if the concern has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Concern> createConcern(@Valid @RequestBody Concern concern) throws URISyntaxException {
        log.debug("REST request to save Concern : {}", concern);
        if (concern.getId() != null) {
            throw new BadRequestAlertException("A new concern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concern result = concernRepository.save(concern);
        return ResponseEntity
            .created(new URI("/api/concerns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concerns/:id} : Updates an existing concern.
     *
     * @param id the id of the concern to save.
     * @param concern the concern to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concern,
     * or with status {@code 400 (Bad Request)} if the concern is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concern couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Concern> updateConcern(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Concern concern
    ) throws URISyntaxException {
        log.debug("REST request to update Concern : {}, {}", id, concern);
        if (concern.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concern.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concernRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Concern result = concernRepository.save(concern);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concern.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concerns/:id} : Partial updates given fields of an existing concern, field will ignore if it is null
     *
     * @param id the id of the concern to save.
     * @param concern the concern to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concern,
     * or with status {@code 400 (Bad Request)} if the concern is not valid,
     * or with status {@code 404 (Not Found)} if the concern is not found,
     * or with status {@code 500 (Internal Server Error)} if the concern couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Concern> partialUpdateConcern(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Concern concern
    ) throws URISyntaxException {
        log.debug("REST request to partial update Concern partially : {}, {}", id, concern);
        if (concern.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concern.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concernRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Concern> result = concernRepository
            .findById(concern.getId())
            .map(existingConcern -> {
                if (concern.getTitle() != null) {
                    existingConcern.setTitle(concern.getTitle());
                }
                if (concern.getTitleFr() != null) {
                    existingConcern.setTitleFr(concern.getTitleFr());
                }
                if (concern.getGender() != null) {
                    existingConcern.setGender(concern.getGender());
                }
                if (concern.getOtherNames() != null) {
                    existingConcern.setOtherNames(concern.getOtherNames());
                }
                if (concern.getOtherNamesFr() != null) {
                    existingConcern.setOtherNamesFr(concern.getOtherNamesFr());
                }
                if (concern.getDescription() != null) {
                    existingConcern.setDescription(concern.getDescription());
                }
                if (concern.getDescriptionFr() != null) {
                    existingConcern.setDescriptionFr(concern.getDescriptionFr());
                }
                if (concern.getPicture() != null) {
                    existingConcern.setPicture(concern.getPicture());
                }
                if (concern.getPictureContentType() != null) {
                    existingConcern.setPictureContentType(concern.getPictureContentType());
                }

                return existingConcern;
            })
            .map(concernRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concern.getId().toString())
        );
    }

    /**
     * {@code GET  /concerns} : get all the concerns.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concerns in body.
     */
    @GetMapping("")
    public List<Concern> getAllConcerns(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Concerns");
        if (eagerload) {
            return concernRepository.findAllWithEagerRelationships();
        } else {
            return concernRepository.findAll();
        }
    }

    /**
     * {@code GET  /concerns/:id} : get the "id" concern.
     *
     * @param id the id of the concern to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concern, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Concern> getConcern(@PathVariable("id") Long id) {
        log.debug("REST request to get Concern : {}", id);
        Optional<Concern> concern = concernRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(concern);
    }

    /**
     * {@code DELETE  /concerns/:id} : delete the "id" concern.
     *
     * @param id the id of the concern to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcern(@PathVariable("id") Long id) {
        log.debug("REST request to delete Concern : {}", id);
        concernRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
