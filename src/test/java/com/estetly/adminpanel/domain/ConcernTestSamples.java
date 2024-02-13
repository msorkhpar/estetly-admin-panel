package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConcernTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Concern getConcernSample1() {
        return new Concern().id(1L).title("title1").otherNames("otherNames1");
    }

    public static Concern getConcernSample2() {
        return new Concern().id(2L).title("title2").otherNames("otherNames2");
    }

    public static Concern getConcernRandomSampleGenerator() {
        return new Concern().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).otherNames(UUID.randomUUID().toString());
    }
}
