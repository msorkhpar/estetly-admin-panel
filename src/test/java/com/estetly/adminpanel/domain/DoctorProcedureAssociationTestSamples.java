package com.estetly.adminpanel.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DoctorProcedureAssociationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DoctorProcedureAssociation getDoctorProcedureAssociationSample1() {
        return new DoctorProcedureAssociation().id(1L);
    }

    public static DoctorProcedureAssociation getDoctorProcedureAssociationSample2() {
        return new DoctorProcedureAssociation().id(2L);
    }

    public static DoctorProcedureAssociation getDoctorProcedureAssociationRandomSampleGenerator() {
        return new DoctorProcedureAssociation().id(longCount.incrementAndGet());
    }
}
