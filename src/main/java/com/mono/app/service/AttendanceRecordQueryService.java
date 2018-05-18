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

import com.mono.app.domain.AttendanceRecord;
import com.mono.app.domain.*; // for static metamodels
import com.mono.app.repository.AttendanceRecordRepository;
import com.mono.app.service.dto.AttendanceRecordCriteria;

import com.mono.app.domain.enumeration.AttendanceType;

/**
 * Service for executing complex queries for AttendanceRecord entities in the database.
 * The main input is a {@link AttendanceRecordCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendanceRecord} or a {@link Page} of {@link AttendanceRecord} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceRecordQueryService extends QueryService<AttendanceRecord> {

    private final Logger log = LoggerFactory.getLogger(AttendanceRecordQueryService.class);


    private final AttendanceRecordRepository attendanceRecordRepository;

    public AttendanceRecordQueryService(AttendanceRecordRepository attendanceRecordRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    /**
     * Return a {@link List} of {@link AttendanceRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceRecord> findByCriteria(AttendanceRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<AttendanceRecord> specification = createSpecification(criteria);
        return attendanceRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AttendanceRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceRecord> findByCriteria(AttendanceRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<AttendanceRecord> specification = createSpecification(criteria);
        return attendanceRecordRepository.findAll(specification, page);
    }

    /**
     * Function to convert AttendanceRecordCriteria to a {@link Specifications}
     */
    private Specifications<AttendanceRecord> createSpecification(AttendanceRecordCriteria criteria) {
        Specifications<AttendanceRecord> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AttendanceRecord_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), AttendanceRecord_.date));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), AttendanceRecord_.type));
            }
            if (criteria.getGarzonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGarzonId(), AttendanceRecord_.garzon, Garzon_.id));
            }
        }
        return specification;
    }

}
