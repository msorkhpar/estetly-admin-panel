package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BodyAreaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BodyArea getBodyAreaSample1() {
        return new BodyArea().id(1L).code("code1").displayName("displayName1");
    }

    public static BodyArea getBodyAreaSample2() {
        return new BodyArea().id(2L).code("code2").displayName("displayName2");
    }

    public static BodyArea getBodyAreaRandomSampleGenerator() {
        return new BodyArea().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).displayName(UUID.randomUUID().toString());
    }
}
