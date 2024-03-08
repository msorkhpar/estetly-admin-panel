package com.estetly.adminpanel.repository;

import com.estetly.adminpanel.domain.PreSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PreSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreSubscriptionRepository extends JpaRepository<PreSubscription, Long> {}
