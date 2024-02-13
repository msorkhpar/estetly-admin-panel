package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.Concern;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Concern entity.
 */
@Repository
public interface ConcernRepository extends JpaRepository<Concern, Long> {
    default Optional<Concern> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Concern> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Concern> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select concern from Concern concern left join fetch concern.category",
        countQuery = "select count(concern) from Concern concern"
    )
    Page<Concern> findAllWithToOneRelationships(Pageable pageable);

    @Query("select concern from Concern concern left join fetch concern.category")
    List<Concern> findAllWithToOneRelationships();

    @Query("select concern from Concern concern left join fetch concern.category where concern.id =:id")
    Optional<Concern> findOneWithToOneRelationships(@Param("id") Long id);
}
