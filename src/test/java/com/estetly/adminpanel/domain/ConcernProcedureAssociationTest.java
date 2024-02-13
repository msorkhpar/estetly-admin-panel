package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.ConcernProcedureAssociationTestSamples.*;
import static com.estetly.adminpanel.domain.ConcernTestSamples.*;
import static com.estetly.adminpanel.domain.ProcedureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConcernProcedureAssociationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcernProcedureAssociation.class);
        ConcernProcedureAssociation concernProcedureAssociation1 = getConcernProcedureAssociationSample1();
        ConcernProcedureAssociation concernProcedureAssociation2 = new ConcernProcedureAssociation();
        assertThat(concernProcedureAssociation1).isNotEqualTo(concernProcedureAssociation2);

        concernProcedureAssociation2.setId(concernProcedureAssociation1.getId());
        assertThat(concernProcedureAssociation1).isEqualTo(concernProcedureAssociation2);

        concernProcedureAssociation2 = getConcernProcedureAssociationSample2();
        assertThat(concernProcedureAssociation1).isNotEqualTo(concernProcedureAssociation2);
    }

    @Test
    void procedureTest() throws Exception {
        ConcernProcedureAssociation concernProcedureAssociation = getConcernProcedureAssociationRandomSampleGenerator();
        Procedure procedureBack = getProcedureRandomSampleGenerator();

        concernProcedureAssociation.setProcedure(procedureBack);
        assertThat(concernProcedureAssociation.getProcedure()).isEqualTo(procedureBack);

        concernProcedureAssociation.procedure(null);
        assertThat(concernProcedureAssociation.getProcedure()).isNull();
    }

    @Test
    void concernTest() throws Exception {
        ConcernProcedureAssociation concernProcedureAssociation = getConcernProcedureAssociationRandomSampleGenerator();
        Concern concernBack = getConcernRandomSampleGenerator();

        concernProcedureAssociation.setConcern(concernBack);
        assertThat(concernProcedureAssociation.getConcern()).isEqualTo(concernBack);

        concernProcedureAssociation.concern(null);
        assertThat(concernProcedureAssociation.getConcern()).isNull();
    }
}
