package com.mono.app.domain;

import com.mono.app.domain.enumeration.AttendanceType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AttendanceRecord.class)
public abstract class AttendanceRecord_ {

	public static volatile SingularAttribute<AttendanceRecord, Instant> date;
	public static volatile SingularAttribute<AttendanceRecord, Long> id;
	public static volatile SingularAttribute<AttendanceRecord, AttendanceType> type;
	public static volatile SingularAttribute<AttendanceRecord, Garzon> garzon;

	public static final String DATE = "date";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String GARZON = "garzon";

}

