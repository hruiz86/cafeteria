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

import com.mono.app.domain.Orden;
import com.mono.app.domain.*; // for static metamodels
import com.mono.app.repository.OrdenRepository;
import com.mono.app.service.dto.OrdenCriteria;

import com.mono.app.domain.enumeration.State;

/**
 * Service for executing complex queries for Orden entities in the database.
 * The main input is a {@link OrdenCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Orden} or a {@link Page} of {@link Orden} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdenQueryService extends QueryService<Orden> {

    private final Logger log = LoggerFactory.getLogger(OrdenQueryService.class);


    private final OrdenRepository ordenRepository;

    public OrdenQueryService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    /**
     * Return a {@link List} of {@link Orden} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Orden> findByCriteria(OrdenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Orden> specification = createSpecification(criteria);
        return ordenRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Orden} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Orden> findByCriteria(OrdenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Orden> specification = createSpecification(criteria);
        return ordenRepository.findAll(specification, page);
    }

    /**
     * Function to convert OrdenCriteria to a {@link Specifications}
     */
    private Specifications<Orden> createSpecification(OrdenCriteria criteria) {
        Specifications<Orden> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Orden_.id));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), Orden_.state));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Orden_.total));
            }
            if (criteria.getGarzonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGarzonId(), Orden_.garzon, Garzon_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProductId(), Orden_.products, Product_.id));
            }
        }
        return specification;
    }

}
