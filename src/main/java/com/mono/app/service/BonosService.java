package com.mono.app.service;

import com.mono.app.domain.Bonos;
import com.mono.app.repository.BonosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bonos.
 */
@Service
@Transactional
public class BonosService {

    private final Logger log = LoggerFactory.getLogger(BonosService.class);

    private final BonosRepository bonosRepository;

    public BonosService(BonosRepository bonosRepository) {
        this.bonosRepository = bonosRepository;
    }

    /**
     * Save a bonos.
     *
     * @param bonos the entity to save
     * @return the persisted entity
     */
    public Bonos save(Bonos bonos) {
        log.debug("Request to save Bonos : {}", bonos);
        return bonosRepository.save(bonos);
    }

    /**
     * Get all the bonos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bonos> findAll(Pageable pageable) {
        log.debug("Request to get all Bonos");
        return bonosRepository.findAll(pageable);
    }

    /**
     * Get one bonos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Bonos findOne(Long id) {
        log.debug("Request to get Bonos : {}", id);
        return bonosRepository.findOne(id);
    }

    /**
     * Delete the bonos by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bonos : {}", id);
        bonosRepository.delete(id);
    }
}
