package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.DoctorProcedureAssociation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DoctorProcedureAssociation entity.
 */
@Repository
public interface DoctorProcedureAssociationRepository extends JpaRepository<DoctorProcedureAssociation, Long> {
    default Optional<DoctorProcedureAssociation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DoctorProcedureAssociation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DoctorProcedureAssociation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select doctorProcedureAssociation from DoctorProcedureAssociation doctorProcedureAssociation left join fetch doctorProcedureAssociation.procedure left join fetch doctorProcedureAssociation.doctor",
        countQuery = "select count(doctorProcedureAssociation) from DoctorProcedureAssociation doctorProcedureAssociation"
    )
    Page<DoctorProcedureAssociation> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select doctorProcedureAssociation from DoctorProcedureAssociation doctorProcedureAssociation left join fetch doctorProcedureAssociation.procedure left join fetch doctorProcedureAssociation.doctor"
    )
    List<DoctorProcedureAssociation> findAllWithToOneRelationships();

    @Query(
        "select doctorProcedureAssociation from DoctorProcedureAssociation doctorProcedureAssociation left join fetch doctorProcedureAssociation.procedure left join fetch doctorProcedureAssociation.doctor where doctorProcedureAssociation.id =:id"
    )
    Optional<DoctorProcedureAssociation> findOneWithToOneRelationships(@Param("id") Long id);
}
