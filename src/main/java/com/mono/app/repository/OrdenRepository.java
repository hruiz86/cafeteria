package com.mono.app.repository;

import com.mono.app.domain.Orden;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Orden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>, JpaSpecificationExecutor<Orden> {
    @Query("select distinct orden from Orden orden left join fetch orden.products")
    List<Orden> findAllWithEagerRelationships();

    @Query("select orden from Orden orden left join fetch orden.products where orden.id =:id")
    Orden findOneWithEagerRelationships(@Param("id") Long id);

}
