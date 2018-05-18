package com.mono.app.service;

import com.mono.app.domain.Orden;
import com.mono.app.repository.OrdenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Orden.
 */
@Service
@Transactional
public class OrdenService {

    private final Logger log = LoggerFactory.getLogger(OrdenService.class);

    private final OrdenRepository ordenRepository;

    public OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    /**
     * Save a orden.
     *
     * @param orden the entity to save
     * @return the persisted entity
     */
    public Orden save(Orden orden) {
        log.debug("Request to save Orden : {}", orden);
        return ordenRepository.save(orden);
    }

    /**
     * Get all the ordens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Orden> findAll(Pageable pageable) {
        log.debug("Request to get all Ordens");
        return ordenRepository.findAll(pageable);
    }

    /**
     * Get one orden by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Orden findOne(Long id) {
        log.debug("Request to get Orden : {}", id);
        return ordenRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the orden by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Orden : {}", id);
        ordenRepository.delete(id);
    }
}
