package com.mono.app.domain;

import com.mono.app.domain.enumeration.BonoType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bonos.class)
public abstract class Bonos_ {

	public static volatile SingularAttribute<Bonos, Double> amount;
	public static volatile SingularAttribute<Bonos, Long> id;
	public static volatile SingularAttribute<Bonos, BonoType> type;
	public static volatile SingularAttribute<Bonos, Garzon> garzon;

	public static final String AMOUNT = "amount";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String GARZON = "garzon";

}

