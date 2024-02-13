package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.BodyAreaConcernAssociation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BodyAreaConcernAssociation entity.
 */
@Repository
public interface BodyAreaConcernAssociationRepository extends JpaRepository<BodyAreaConcernAssociation, Long> {
    default Optional<BodyAreaConcernAssociation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BodyAreaConcernAssociation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BodyAreaConcernAssociation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bodyAreaConcernAssociation from BodyAreaConcernAssociation bodyAreaConcernAssociation left join fetch bodyAreaConcernAssociation.bodyArea left join fetch bodyAreaConcernAssociation.concern",
        countQuery = "select count(bodyAreaConcernAssociation) from BodyAreaConcernAssociation bodyAreaConcernAssociation"
    )
    Page<BodyAreaConcernAssociation> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select bodyAreaConcernAssociation from BodyAreaConcernAssociation bodyAreaConcernAssociation left join fetch bodyAreaConcernAssociation.bodyArea left join fetch bodyAreaConcernAssociation.concern"
    )
    List<BodyAreaConcernAssociation> findAllWithToOneRelationships();

    @Query(
        "select bodyAreaConcernAssociation from BodyAreaConcernAssociation bodyAreaConcernAssociation left join fetch bodyAreaConcernAssociation.bodyArea left join fetch bodyAreaConcernAssociation.concern where bodyAreaConcernAssociation.id =:id"
    )
    Optional<BodyAreaConcernAssociation> findOneWithToOneRelationships(@Param("id") Long id);
}
