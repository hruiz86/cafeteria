package com.mono.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mono.app.domain.Bonos;
import com.mono.app.service.BonosService;
import com.mono.app.web.rest.errors.BadRequestAlertException;
import com.mono.app.web.rest.util.HeaderUtil;
import com.mono.app.web.rest.util.PaginationUtil;
import com.mono.app.service.dto.BonosCriteria;
import com.mono.app.service.BonosQueryService;
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
 * REST controller for managing Bonos.
 */
@RestController
@RequestMapping("/api")
public class BonosResource {

    private final Logger log = LoggerFactory.getLogger(BonosResource.class);

    private static final String ENTITY_NAME = "bonos";

    private final BonosService bonosService;

    private final BonosQueryService bonosQueryService;

    public BonosResource(BonosService bonosService, BonosQueryService bonosQueryService) {
        this.bonosService = bonosService;
        this.bonosQueryService = bonosQueryService;
    }

    /**
     * POST  /bonos : Create a new bonos.
     *
     * @param bonos the bonos to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonos, or with status 400 (Bad Request) if the bonos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bonos")
    @Timed
    public ResponseEntity<Bonos> createBonos(@Valid @RequestBody Bonos bonos) throws URISyntaxException {
        log.debug("REST request to save Bonos : {}", bonos);
        if (bonos.getId() != null) {
            throw new BadRequestAlertException("A new bonos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bonos result = bonosService.save(bonos);
        return ResponseEntity.created(new URI("/api/bonos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bonos : Updates an existing bonos.
     *
     * @param bonos the bonos to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonos,
     * or with status 400 (Bad Request) if the bonos is not valid,
     * or with status 500 (Internal Server Error) if the bonos couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bonos")
    @Timed
    public ResponseEntity<Bonos> updateBonos(@Valid @RequestBody Bonos bonos) throws URISyntaxException {
        log.debug("REST request to update Bonos : {}", bonos);
        if (bonos.getId() == null) {
            return createBonos(bonos);
        }
        Bonos result = bonosService.save(bonos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonos.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bonos : get all the bonos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bonos in body
     */
    @GetMapping("/bonos")
    @Timed
    public ResponseEntity<List<Bonos>> getAllBonos(BonosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bonos by criteria: {}", criteria);
        Page<Bonos> page = bonosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bonos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bonos/:id : get the "id" bonos.
     *
     * @param id the id of the bonos to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonos, or with status 404 (Not Found)
     */
    @GetMapping("/bonos/{id}")
    @Timed
    public ResponseEntity<Bonos> getBonos(@PathVariable Long id) {
        log.debug("REST request to get Bonos : {}", id);
        Bonos bonos = bonosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonos));
    }

    /**
     * DELETE  /bonos/:id : delete the "id" bonos.
     *
     * @param id the id of the bonos to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bonos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonos(@PathVariable Long id) {
        log.debug("REST request to delete Bonos : {}", id);
        bonosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
