package com.mono.app.service;

import com.mono.app.domain.Garzon;
import com.mono.app.repository.GarzonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Garzon.
 */
@Service
@Transactional
public class GarzonService {

    private final Logger log = LoggerFactory.getLogger(GarzonService.class);

    private final GarzonRepository garzonRepository;

    public GarzonService(GarzonRepository garzonRepository) {
        this.garzonRepository = garzonRepository;
    }

    /**
     * Save a garzon.
     *
     * @param garzon the entity to save
     * @return the persisted entity
     */
    public Garzon save(Garzon garzon) {
        log.debug("Request to save Garzon : {}", garzon);
        return garzonRepository.save(garzon);
    }

    /**
     * Get all the garzons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Garzon> findAll(Pageable pageable) {
        log.debug("Request to get all Garzons");
        return garzonRepository.findAll(pageable);
    }

    /**
     * Get one garzon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Garzon findOne(Long id) {
        log.debug("Request to get Garzon : {}", id);
        return garzonRepository.findOne(id);
    }

    /**
     * Delete the garzon by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Garzon : {}", id);
        garzonRepository.delete(id);
    }
}
