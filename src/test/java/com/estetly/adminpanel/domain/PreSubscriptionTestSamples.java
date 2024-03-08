package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PreSubscriptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PreSubscription getPreSubscriptionSample1() {
        return new PreSubscription().id(1L).fullname("fullname1").email("email1").phoneNumber("phoneNumber1");
    }

    public static PreSubscription getPreSubscriptionSample2() {
        return new PreSubscription().id(2L).fullname("fullname2").email("email2").phoneNumber("phoneNumber2");
    }

    public static PreSubscription getPreSubscriptionRandomSampleGenerator() {
        return new PreSubscription()
            .id(longCount.incrementAndGet())
            .fullname(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString());
    }
}
