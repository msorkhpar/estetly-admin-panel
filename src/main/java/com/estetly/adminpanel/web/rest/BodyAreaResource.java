package com.estetly.adminpanel.web.rest;

import com.estetly.adminpanel.domain.BodyArea;
import com.estetly.adminpanel.repository.BodyAreaRepository;
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
 * REST controller for managing {@link com.estetly.adminpanel.domain.BodyArea}.
 */
@RestController
@RequestMapping("/api/body-areas")
@Transactional
public class BodyAreaResource {

    private final Logger log = LoggerFactory.getLogger(BodyAreaResource.class);

    private static final String ENTITY_NAME = "bodyArea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyAreaRepository bodyAreaRepository;

    public BodyAreaResource(BodyAreaRepository bodyAreaRepository) {
        this.bodyAreaRepository = bodyAreaRepository;
    }

    /**
     * {@code POST  /body-areas} : Create a new bodyArea.
     *
     * @param bodyArea the bodyArea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyArea, or with status {@code 400 (Bad Request)} if the bodyArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BodyArea> createBodyArea(@Valid @RequestBody BodyArea bodyArea) throws URISyntaxException {
        log.debug("REST request to save BodyArea : {}", bodyArea);
        if (bodyArea.getId() != null) {
            throw new BadRequestAlertException("A new bodyArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyArea result = bodyAreaRepository.save(bodyArea);
        return ResponseEntity
            .created(new URI("/api/body-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /body-areas/:id} : Updates an existing bodyArea.
     *
     * @param id the id of the bodyArea to save.
     * @param bodyArea the bodyArea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyArea,
     * or with status {@code 400 (Bad Request)} if the bodyArea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyArea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BodyArea> updateBodyArea(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BodyArea bodyArea
    ) throws URISyntaxException {
        log.debug("REST request to update BodyArea : {}, {}", id, bodyArea);
        if (bodyArea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyArea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BodyArea result = bodyAreaRepository.save(bodyArea);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyArea.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /body-areas/:id} : Partial updates given fields of an existing bodyArea, field will ignore if it is null
     *
     * @param id the id of the bodyArea to save.
     * @param bodyArea the bodyArea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyArea,
     * or with status {@code 400 (Bad Request)} if the bodyArea is not valid,
     * or with status {@code 404 (Not Found)} if the bodyArea is not found,
     * or with status {@code 500 (Internal Server Error)} if the bodyArea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BodyArea> partialUpdateBodyArea(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BodyArea bodyArea
    ) throws URISyntaxException {
        log.debug("REST request to partial update BodyArea partially : {}, {}", id, bodyArea);
        if (bodyArea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyArea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BodyArea> result = bodyAreaRepository
            .findById(bodyArea.getId())
            .map(existingBodyArea -> {
                if (bodyArea.getCode() != null) {
                    existingBodyArea.setCode(bodyArea.getCode());
                }
                if (bodyArea.getDisplayName() != null) {
                    existingBodyArea.setDisplayName(bodyArea.getDisplayName());
                }

                return existingBodyArea;
            })
            .map(bodyAreaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyArea.getId().toString())
        );
    }

    /**
     * {@code GET  /body-areas} : get all the bodyAreas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodyAreas in body.
     */
    @GetMapping("")
    public List<BodyArea> getAllBodyAreas() {
        log.debug("REST request to get all BodyAreas");
        return bodyAreaRepository.findAll();
    }

    /**
     * {@code GET  /body-areas/:id} : get the "id" bodyArea.
     *
     * @param id the id of the bodyArea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyArea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BodyArea> getBodyArea(@PathVariable("id") Long id) {
        log.debug("REST request to get BodyArea : {}", id);
        Optional<BodyArea> bodyArea = bodyAreaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bodyArea);
    }

    /**
     * {@code DELETE  /body-areas/:id} : delete the "id" bodyArea.
     *
     * @param id the id of the bodyArea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyArea(@PathVariable("id") Long id) {
        log.debug("REST request to delete BodyArea : {}", id);
        bodyAreaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
