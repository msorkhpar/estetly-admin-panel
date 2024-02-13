package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BodyAreaConcernAssociationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BodyAreaConcernAssociation getBodyAreaConcernAssociationSample1() {
        return new BodyAreaConcernAssociation().id(1L);
    }

    public static BodyAreaConcernAssociation getBodyAreaConcernAssociationSample2() {
        return new BodyAreaConcernAssociation().id(2L);
    }

    public static BodyAreaConcernAssociation getBodyAreaConcernAssociationRandomSampleGenerator() {
        return new BodyAreaConcernAssociation().id(longCount.incrementAndGet());
    }
}
