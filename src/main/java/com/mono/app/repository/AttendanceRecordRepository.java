package com.mono.app.repository;

import com.mono.app.domain.AttendanceRecord;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AttendanceRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long>, JpaSpecificationExecutor<AttendanceRecord> {

}
