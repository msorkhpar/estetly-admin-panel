package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.ConcernProcedureAssociation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConcernProcedureAssociation entity.
 */
@Repository
public interface ConcernProcedureAssociationRepository extends JpaRepository<ConcernProcedureAssociation, Long> {
    default Optional<ConcernProcedureAssociation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ConcernProcedureAssociation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ConcernProcedureAssociation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select concernProcedureAssociation from ConcernProcedureAssociation concernProcedureAssociation left join fetch concernProcedureAssociation.procedure left join fetch concernProcedureAssociation.concern",
        countQuery = "select count(concernProcedureAssociation) from ConcernProcedureAssociation concernProcedureAssociation"
    )
    Page<ConcernProcedureAssociation> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select concernProcedureAssociation from ConcernProcedureAssociation concernProcedureAssociation left join fetch concernProcedureAssociation.procedure left join fetch concernProcedureAssociation.concern"
    )
    List<ConcernProcedureAssociation> findAllWithToOneRelationships();

    @Query(
        "select concernProcedureAssociation from ConcernProcedureAssociation concernProcedureAssociation left join fetch concernProcedureAssociation.procedure left join fetch concernProcedureAssociation.concern where concernProcedureAssociation.id =:id"
    )
    Optional<ConcernProcedureAssociation> findOneWithToOneRelationships(@Param("id") Long id);
}
