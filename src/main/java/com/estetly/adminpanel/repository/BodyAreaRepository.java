package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.BodyArea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BodyArea entity.
 */
@Repository
public interface BodyAreaRepository extends JpaRepository<BodyArea, Long> {
    default Optional<BodyArea> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BodyArea> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BodyArea> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bodyArea from BodyArea bodyArea left join fetch bodyArea.parent",
        countQuery = "select count(bodyArea) from BodyArea bodyArea"
    )
    Page<BodyArea> findAllWithToOneRelationships(Pageable pageable);

    @Query("select bodyArea from BodyArea bodyArea left join fetch bodyArea.parent")
    List<BodyArea> findAllWithToOneRelationships();

    @Query("select bodyArea from BodyArea bodyArea left join fetch bodyArea.parent where bodyArea.id =:id")
    Optional<BodyArea> findOneWithToOneRelationships(@Param("id") Long id);
}
