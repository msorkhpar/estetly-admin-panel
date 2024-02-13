package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.ProcedureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcedureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedure.class);
        Procedure procedure1 = getProcedureSample1();
        Procedure procedure2 = new Procedure();
        assertThat(procedure1).isNotEqualTo(procedure2);

        procedure2.setId(procedure1.getId());
        assertThat(procedure1).isEqualTo(procedure2);

        procedure2 = getProcedureSample2();
        assertThat(procedure1).isNotEqualTo(procedure2);
    }
}
