package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.BodyArea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BodyArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyAreaRepository extends JpaRepository<BodyArea, Long> {}
