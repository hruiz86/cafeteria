package com.mono.app.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Double> price;
	public static volatile SetAttribute<Product, Orden> ordens;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Integer> stock;

	public static final String PRICE = "price";
	public static final String ORDENS = "ordens";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String STOCK = "stock";

}

