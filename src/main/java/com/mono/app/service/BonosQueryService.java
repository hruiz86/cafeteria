package com.mono.app.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mono.app.domain.Bonos;
import com.mono.app.domain.*; // for static metamodels
import com.mono.app.repository.BonosRepository;
import com.mono.app.service.dto.BonosCriteria;

import com.mono.app.domain.enumeration.BonoType;

/**
 * Service for executing complex queries for Bonos entities in the database.
 * The main input is a {@link BonosCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bonos} or a {@link Page} of {@link Bonos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BonosQueryService extends QueryService<Bonos> {

    private final Logger log = LoggerFactory.getLogger(BonosQueryService.class);


    private final BonosRepository bonosRepository;

    public BonosQueryService(BonosRepository bonosRepository) {
        this.bonosRepository = bonosRepository;
    }

    /**
     * Return a {@link List} of {@link Bonos} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bonos> findByCriteria(BonosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Bonos> specification = createSpecification(criteria);
        return bonosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bonos} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bonos> findByCriteria(BonosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Bonos> specification = createSpecification(criteria);
        return bonosRepository.findAll(specification, page);
    }

    /**
     * Function to convert BonosCriteria to a {@link Specifications}
     */
    private Specifications<Bonos> createSpecification(BonosCriteria criteria) {
        Specifications<Bonos> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Bonos_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Bonos_.type));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Bonos_.amount));
            }
            if (criteria.getGarzonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGarzonId(), Bonos_.garzons, Garzon_.id));
            }
        }
        return specification;
    }

}
