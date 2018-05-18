package com.mono.app.domain;

import com.mono.app.domain.enumeration.State;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orden.class)
public abstract class Orden_ {

	public static volatile SingularAttribute<Orden, Double> total;
	public static volatile SingularAttribute<Orden, Long> id;
	public static volatile SingularAttribute<Orden, State> state;
	public static volatile SingularAttribute<Orden, Garzon> garzon;
	public static volatile SetAttribute<Orden, Product> products;

	public static final String TOTAL = "total";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String GARZON = "garzon";
	public static final String PRODUCTS = "products";

}

