package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ConcernProcedureAssociationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ConcernProcedureAssociation getConcernProcedureAssociationSample1() {
        return new ConcernProcedureAssociation().id(1L);
    }

    public static ConcernProcedureAssociation getConcernProcedureAssociationSample2() {
        return new ConcernProcedureAssociation().id(2L);
    }

    public static ConcernProcedureAssociation getConcernProcedureAssociationRandomSampleGenerator() {
        return new ConcernProcedureAssociation().id(longCount.incrementAndGet());
    }
}
