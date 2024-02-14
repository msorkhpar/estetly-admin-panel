package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProcedureTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Procedure getProcedureSample1() {
        return new Procedure().id(1L).title("title1").invasiveness(1);
    }

    public static Procedure getProcedureSample2() {
        return new Procedure().id(2L).title("title2").invasiveness(2);
    }

    public static Procedure getProcedureRandomSampleGenerator() {
        return new Procedure().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).invasiveness(intCount.incrementAndGet());
    }
}
