package com.mono.app.repository;

import com.mono.app.domain.Garzon;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Garzon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GarzonRepository extends JpaRepository<Garzon, Long>, JpaSpecificationExecutor<Garzon> {

}
