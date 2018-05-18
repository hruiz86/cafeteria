package com.mono.app.repository;

import com.mono.app.domain.Bonos;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bonos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonosRepository extends JpaRepository<Bonos, Long>, JpaSpecificationExecutor<Bonos> {

}
