package com.mono.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mono.app.domain.Garzon;
import com.mono.app.service.GarzonService;
import com.mono.app.web.rest.errors.BadRequestAlertException;
import com.mono.app.web.rest.util.HeaderUtil;
import com.mono.app.web.rest.util.PaginationUtil;
import com.mono.app.service.dto.GarzonCriteria;
import com.mono.app.service.GarzonQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Garzon.
 */
@RestController
@RequestMapping("/api")
public class GarzonResource {

    private final Logger log = LoggerFactory.getLogger(GarzonResource.class);

    private static final String ENTITY_NAME = "garzon";

    private final GarzonService garzonService;

    private final GarzonQueryService garzonQueryService;

    public GarzonResource(GarzonService garzonService, GarzonQueryService garzonQueryService) {
        this.garzonService = garzonService;
        this.garzonQueryService = garzonQueryService;
    }

    /**
     * POST  /garzons : Create a new garzon.
     *
     * @param garzon the garzon to create
     * @return the ResponseEntity with status 201 (Created) and with body the new garzon, or with status 400 (Bad Request) if the garzon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/garzons")
    @Timed
    public ResponseEntity<Garzon> createGarzon(@Valid @RequestBody Garzon garzon) throws URISyntaxException {
        log.debug("REST request to save Garzon : {}", garzon);
        if (garzon.getId() != null) {
            throw new BadRequestAlertException("A new garzon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Garzon result = garzonService.save(garzon);
        return ResponseEntity.created(new URI("/api/garzons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /garzons : Updates an existing garzon.
     *
     * @param garzon the garzon to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated garzon,
     * or with status 400 (Bad Request) if the garzon is not valid,
     * or with status 500 (Internal Server Error) if the garzon couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/garzons")
    @Timed
    public ResponseEntity<Garzon> updateGarzon(@Valid @RequestBody Garzon garzon) throws URISyntaxException {
        log.debug("REST request to update Garzon : {}", garzon);
        if (garzon.getId() == null) {
            return createGarzon(garzon);
        }
        Garzon result = garzonService.save(garzon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, garzon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /garzons : get all the garzons.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of garzons in body
     */
    @GetMapping("/garzons")
    @Timed
    public ResponseEntity<List<Garzon>> getAllGarzons(GarzonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Garzons by criteria: {}", criteria);
        Page<Garzon> page = garzonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/garzons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /garzons/:id : get the "id" garzon.
     *
     * @param id the id of the garzon to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the garzon, or with status 404 (Not Found)
     */
    @GetMapping("/garzons/{id}")
    @Timed
    public ResponseEntity<Garzon> getGarzon(@PathVariable Long id) {
        log.debug("REST request to get Garzon : {}", id);
        Garzon garzon = garzonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(garzon));
    }

    /**
     * DELETE  /garzons/:id : delete the "id" garzon.
     *
     * @param id the id of the garzon to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/garzons/{id}")
    @Timed
    public ResponseEntity<Void> deleteGarzon(@PathVariable Long id) {
        log.debug("REST request to delete Garzon : {}", id);
        garzonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
