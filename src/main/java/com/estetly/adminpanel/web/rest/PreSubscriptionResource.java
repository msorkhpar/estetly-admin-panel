package com.estetly.adminpanel.web.rest;

import com.estetly.adminpanel.domain.PreSubscription;
import com.estetly.adminpanel.repository.PreSubscriptionRepository;
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
 * REST controller for managing {@link com.estetly.adminpanel.domain.PreSubscription}.
 */
@RestController
@RequestMapping("/api/pre-subscriptions")
@Transactional
public class PreSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(PreSubscriptionResource.class);

    private static final String ENTITY_NAME = "preSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreSubscriptionRepository preSubscriptionRepository;

    public PreSubscriptionResource(PreSubscriptionRepository preSubscriptionRepository) {
        this.preSubscriptionRepository = preSubscriptionRepository;
    }

    /**
     * {@code POST  /pre-subscriptions} : Create a new preSubscription.
     *
     * @param preSubscription the preSubscription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preSubscription, or with status {@code 400 (Bad Request)} if the preSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PreSubscription> createPreSubscription(@Valid @RequestBody PreSubscription preSubscription)
        throws URISyntaxException {
        log.debug("REST request to save PreSubscription : {}", preSubscription);
        if (preSubscription.getId() != null) {
            throw new BadRequestAlertException("A new preSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreSubscription result = preSubscriptionRepository.save(preSubscription);
        return ResponseEntity
            .created(new URI("/api/pre-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pre-subscriptions/:id} : Updates an existing preSubscription.
     *
     * @param id the id of the preSubscription to save.
     * @param preSubscription the preSubscription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preSubscription,
     * or with status {@code 400 (Bad Request)} if the preSubscription is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preSubscription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PreSubscription> updatePreSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PreSubscription preSubscription
    ) throws URISyntaxException {
        log.debug("REST request to update PreSubscription : {}, {}", id, preSubscription);
        if (preSubscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preSubscription.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreSubscription result = preSubscriptionRepository.save(preSubscription);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preSubscription.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pre-subscriptions/:id} : Partial updates given fields of an existing preSubscription, field will ignore if it is null
     *
     * @param id the id of the preSubscription to save.
     * @param preSubscription the preSubscription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preSubscription,
     * or with status {@code 400 (Bad Request)} if the preSubscription is not valid,
     * or with status {@code 404 (Not Found)} if the preSubscription is not found,
     * or with status {@code 500 (Internal Server Error)} if the preSubscription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreSubscription> partialUpdatePreSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PreSubscription preSubscription
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreSubscription partially : {}, {}", id, preSubscription);
        if (preSubscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preSubscription.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreSubscription> result = preSubscriptionRepository
            .findById(preSubscription.getId())
            .map(existingPreSubscription -> {
                if (preSubscription.getSubscriberType() != null) {
                    existingPreSubscription.setSubscriberType(preSubscription.getSubscriberType());
                }
                if (preSubscription.getFullname() != null) {
                    existingPreSubscription.setFullname(preSubscription.getFullname());
                }
                if (preSubscription.getEmail() != null) {
                    existingPreSubscription.setEmail(preSubscription.getEmail());
                }
                if (preSubscription.getPhoneNumber() != null) {
                    existingPreSubscription.setPhoneNumber(preSubscription.getPhoneNumber());
                }
                if (preSubscription.getTimestamp() != null) {
                    existingPreSubscription.setTimestamp(preSubscription.getTimestamp());
                }
                if (preSubscription.getEmailStatus() != null) {
                    existingPreSubscription.setEmailStatus(preSubscription.getEmailStatus());
                }
                if (preSubscription.getSubscriberStatus() != null) {
                    existingPreSubscription.setSubscriberStatus(preSubscription.getSubscriberStatus());
                }

                return existingPreSubscription;
            })
            .map(preSubscriptionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preSubscription.getId().toString())
        );
    }

    /**
     * {@code GET  /pre-subscriptions} : get all the preSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preSubscriptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PreSubscription>> getAllPreSubscriptions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PreSubscriptions");
        Page<PreSubscription> page = preSubscriptionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pre-subscriptions/:id} : get the "id" preSubscription.
     *
     * @param id the id of the preSubscription to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preSubscription, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PreSubscription> getPreSubscription(@PathVariable("id") Long id) {
        log.debug("REST request to get PreSubscription : {}", id);
        Optional<PreSubscription> preSubscription = preSubscriptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(preSubscription);
    }

    /**
     * {@code DELETE  /pre-subscriptions/:id} : delete the "id" preSubscription.
     *
     * @param id the id of the preSubscription to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreSubscription(@PathVariable("id") Long id) {
        log.debug("REST request to delete PreSubscription : {}", id);
        preSubscriptionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
