package com.mono.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mono.app.domain.Orden;
import com.mono.app.service.OrdenService;
import com.mono.app.web.rest.errors.BadRequestAlertException;
import com.mono.app.web.rest.util.HeaderUtil;
import com.mono.app.web.rest.util.PaginationUtil;
import com.mono.app.service.dto.OrdenCriteria;
import com.mono.app.service.OrdenQueryService;
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
 * REST controller for managing Orden.
 */
@RestController
@RequestMapping("/api")
public class OrdenResource {

    private final Logger log = LoggerFactory.getLogger(OrdenResource.class);

    private static final String ENTITY_NAME = "orden";

    private final OrdenService ordenService;

    private final OrdenQueryService ordenQueryService;

    public OrdenResource(OrdenService ordenService, OrdenQueryService ordenQueryService) {
        this.ordenService = ordenService;
        this.ordenQueryService = ordenQueryService;
    }

    /**
     * POST  /ordens : Create a new orden.
     *
     * @param orden the orden to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orden, or with status 400 (Bad Request) if the orden has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordens")
    @Timed
    public ResponseEntity<Orden> createOrden(@Valid @RequestBody Orden orden) throws URISyntaxException {
        log.debug("REST request to save Orden : {}", orden);
        if (orden.getId() != null) {
            throw new BadRequestAlertException("A new orden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Orden result = ordenService.save(orden);
        return ResponseEntity.created(new URI("/api/ordens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordens : Updates an existing orden.
     *
     * @param orden the orden to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orden,
     * or with status 400 (Bad Request) if the orden is not valid,
     * or with status 500 (Internal Server Error) if the orden couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordens")
    @Timed
    public ResponseEntity<Orden> updateOrden(@Valid @RequestBody Orden orden) throws URISyntaxException {
        log.debug("REST request to update Orden : {}", orden);
        if (orden.getId() == null) {
            return createOrden(orden);
        }
        Orden result = ordenService.save(orden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orden.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordens : get all the ordens.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ordens in body
     */
    @GetMapping("/ordens")
    @Timed
    public ResponseEntity<List<Orden>> getAllOrdens(OrdenCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ordens by criteria: {}", criteria);
        Page<Orden> page = ordenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordens/:id : get the "id" orden.
     *
     * @param id the id of the orden to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orden, or with status 404 (Not Found)
     */
    @GetMapping("/ordens/{id}")
    @Timed
    public ResponseEntity<Orden> getOrden(@PathVariable Long id) {
        log.debug("REST request to get Orden : {}", id);
        Orden orden = ordenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orden));
    }

    /**
     * DELETE  /ordens/:id : delete the "id" orden.
     *
     * @param id the id of the orden to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordens/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
        log.debug("REST request to delete Orden : {}", id);
        ordenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
