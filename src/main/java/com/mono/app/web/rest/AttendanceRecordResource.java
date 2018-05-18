package com.mono.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mono.app.domain.AttendanceRecord;
import com.mono.app.service.AttendanceRecordService;
import com.mono.app.web.rest.errors.BadRequestAlertException;
import com.mono.app.web.rest.util.HeaderUtil;
import com.mono.app.web.rest.util.PaginationUtil;
import com.mono.app.service.dto.AttendanceRecordCriteria;
import com.mono.app.service.AttendanceRecordQueryService;
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
 * REST controller for managing AttendanceRecord.
 */
@RestController
@RequestMapping("/api")
public class AttendanceRecordResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceRecordResource.class);

    private static final String ENTITY_NAME = "attendanceRecord";

    private final AttendanceRecordService attendanceRecordService;

    private final AttendanceRecordQueryService attendanceRecordQueryService;

    public AttendanceRecordResource(AttendanceRecordService attendanceRecordService, AttendanceRecordQueryService attendanceRecordQueryService) {
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceRecordQueryService = attendanceRecordQueryService;
    }

    /**
     * POST  /attendance-records : Create a new attendanceRecord.
     *
     * @param attendanceRecord the attendanceRecord to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendanceRecord, or with status 400 (Bad Request) if the attendanceRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendance-records")
    @Timed
    public ResponseEntity<AttendanceRecord> createAttendanceRecord(@Valid @RequestBody AttendanceRecord attendanceRecord) throws URISyntaxException {
        log.debug("REST request to save AttendanceRecord : {}", attendanceRecord);
        if (attendanceRecord.getId() != null) {
            throw new BadRequestAlertException("A new attendanceRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceRecord result = attendanceRecordService.save(attendanceRecord);
        return ResponseEntity.created(new URI("/api/attendance-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendance-records : Updates an existing attendanceRecord.
     *
     * @param attendanceRecord the attendanceRecord to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendanceRecord,
     * or with status 400 (Bad Request) if the attendanceRecord is not valid,
     * or with status 500 (Internal Server Error) if the attendanceRecord couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendance-records")
    @Timed
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(@Valid @RequestBody AttendanceRecord attendanceRecord) throws URISyntaxException {
        log.debug("REST request to update AttendanceRecord : {}", attendanceRecord);
        if (attendanceRecord.getId() == null) {
            return createAttendanceRecord(attendanceRecord);
        }
        AttendanceRecord result = attendanceRecordService.save(attendanceRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendanceRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendance-records : get all the attendanceRecords.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of attendanceRecords in body
     */
    @GetMapping("/attendance-records")
    @Timed
    public ResponseEntity<List<AttendanceRecord>> getAllAttendanceRecords(AttendanceRecordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AttendanceRecords by criteria: {}", criteria);
        Page<AttendanceRecord> page = attendanceRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attendance-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /attendance-records/:id : get the "id" attendanceRecord.
     *
     * @param id the id of the attendanceRecord to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendanceRecord, or with status 404 (Not Found)
     */
    @GetMapping("/attendance-records/{id}")
    @Timed
    public ResponseEntity<AttendanceRecord> getAttendanceRecord(@PathVariable Long id) {
        log.debug("REST request to get AttendanceRecord : {}", id);
        AttendanceRecord attendanceRecord = attendanceRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attendanceRecord));
    }

    /**
     * DELETE  /attendance-records/:id : delete the "id" attendanceRecord.
     *
     * @param id the id of the attendanceRecord to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendance-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceRecord : {}", id);
        attendanceRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
