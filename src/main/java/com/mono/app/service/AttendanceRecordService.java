package com.mono.app.service;

import com.mono.app.domain.AttendanceRecord;
import com.mono.app.repository.AttendanceRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AttendanceRecord.
 */
@Service
@Transactional
public class AttendanceRecordService {

    private final Logger log = LoggerFactory.getLogger(AttendanceRecordService.class);

    private final AttendanceRecordRepository attendanceRecordRepository;

    public AttendanceRecordService(AttendanceRecordRepository attendanceRecordRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    /**
     * Save a attendanceRecord.
     *
     * @param attendanceRecord the entity to save
     * @return the persisted entity
     */
    public AttendanceRecord save(AttendanceRecord attendanceRecord) {
        log.debug("Request to save AttendanceRecord : {}", attendanceRecord);
        return attendanceRecordRepository.save(attendanceRecord);
    }

    /**
     * Get all the attendanceRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendanceRecord> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceRecords");
        return attendanceRecordRepository.findAll(pageable);
    }

    /**
     * Get one attendanceRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AttendanceRecord findOne(Long id) {
        log.debug("Request to get AttendanceRecord : {}", id);
        return attendanceRecordRepository.findOne(id);
    }

    /**
     * Delete the attendanceRecord by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceRecord : {}", id);
        attendanceRecordRepository.delete(id);
    }
}
