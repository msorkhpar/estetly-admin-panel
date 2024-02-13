package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.DoctorProcedureAssociationTestSamples.*;
import static com.estetly.adminpanel.domain.DoctorTestSamples.*;
import static com.estetly.adminpanel.domain.ProcedureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorProcedureAssociationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorProcedureAssociation.class);
        DoctorProcedureAssociation doctorProcedureAssociation1 = getDoctorProcedureAssociationSample1();
        DoctorProcedureAssociation doctorProcedureAssociation2 = new DoctorProcedureAssociation();
        assertThat(doctorProcedureAssociation1).isNotEqualTo(doctorProcedureAssociation2);

        doctorProcedureAssociation2.setId(doctorProcedureAssociation1.getId());
        assertThat(doctorProcedureAssociation1).isEqualTo(doctorProcedureAssociation2);

        doctorProcedureAssociation2 = getDoctorProcedureAssociationSample2();
        assertThat(doctorProcedureAssociation1).isNotEqualTo(doctorProcedureAssociation2);
    }

    @Test
    void procedureTest() throws Exception {
        DoctorProcedureAssociation doctorProcedureAssociation = getDoctorProcedureAssociationRandomSampleGenerator();
        Procedure procedureBack = getProcedureRandomSampleGenerator();

        doctorProcedureAssociation.setProcedure(procedureBack);
        assertThat(doctorProcedureAssociation.getProcedure()).isEqualTo(procedureBack);

        doctorProcedureAssociation.procedure(null);
        assertThat(doctorProcedureAssociation.getProcedure()).isNull();
    }

    @Test
    void doctorTest() throws Exception {
        DoctorProcedureAssociation doctorProcedureAssociation = getDoctorProcedureAssociationRandomSampleGenerator();
        Doctor doctorBack = getDoctorRandomSampleGenerator();

        doctorProcedureAssociation.setDoctor(doctorBack);
        assertThat(doctorProcedureAssociation.getDoctor()).isEqualTo(doctorBack);

        doctorProcedureAssociation.doctor(null);
        assertThat(doctorProcedureAssociation.getDoctor()).isNull();
    }
}
