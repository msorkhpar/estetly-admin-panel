package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.DoctorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doctor.class);
        Doctor doctor1 = getDoctorSample1();
        Doctor doctor2 = new Doctor();
        assertThat(doctor1).isNotEqualTo(doctor2);

        doctor2.setId(doctor1.getId());
        assertThat(doctor1).isEqualTo(doctor2);

        doctor2 = getDoctorSample2();
        assertThat(doctor1).isNotEqualTo(doctor2);
    }
}
