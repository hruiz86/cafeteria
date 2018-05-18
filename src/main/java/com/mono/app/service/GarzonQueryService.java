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

import com.mono.app.domain.Garzon;
import com.mono.app.domain.*; // for static metamodels
import com.mono.app.repository.GarzonRepository;
import com.mono.app.service.dto.GarzonCriteria;


/**
 * Service for executing complex queries for Garzon entities in the database.
 * The main input is a {@link GarzonCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Garzon} or a {@link Page} of {@link Garzon} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GarzonQueryService extends QueryService<Garzon> {

    private final Logger log = LoggerFactory.getLogger(GarzonQueryService.class);


    private final GarzonRepository garzonRepository;

    public GarzonQueryService(GarzonRepository garzonRepository) {
        this.garzonRepository = garzonRepository;
    }

    /**
     * Return a {@link List} of {@link Garzon} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Garzon> findByCriteria(GarzonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Garzon> specification = createSpecification(criteria);
        return garzonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Garzon} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Garzon> findByCriteria(GarzonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Garzon> specification = createSpecification(criteria);
        return garzonRepository.findAll(specification, page);
    }

    /**
     * Function to convert GarzonCriteria to a {@link Specifications}
     */
    private Specifications<Garzon> createSpecification(GarzonCriteria criteria) {
        Specifications<Garzon> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Garzon_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), Garzon_.userId));
            }
            if (criteria.getPicture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPicture(), Garzon_.picture));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Garzon_.name));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Garzon_.lastName));
            }
            if (criteria.getOrdenId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOrdenId(), Garzon_.orden, Orden_.id));
            }
            if (criteria.getBonosId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBonosId(), Garzon_.bonos, Bonos_.id));
            }
            if (criteria.getAttendanceRecordId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAttendanceRecordId(), Garzon_.attendanceRecord, AttendanceRecord_.id));
            }
        }
        return specification;
    }

}
