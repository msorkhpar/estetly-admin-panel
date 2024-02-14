package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.BodyAreaTestSamples.*;
import static com.estetly.adminpanel.domain.BodyAreaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BodyAreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyArea.class);
        BodyArea bodyArea1 = getBodyAreaSample1();
        BodyArea bodyArea2 = new BodyArea();
        assertThat(bodyArea1).isNotEqualTo(bodyArea2);

        bodyArea2.setId(bodyArea1.getId());
        assertThat(bodyArea1).isEqualTo(bodyArea2);

        bodyArea2 = getBodyAreaSample2();
        assertThat(bodyArea1).isNotEqualTo(bodyArea2);
    }

    @Test
    void parentTest() throws Exception {
        BodyArea bodyArea = getBodyAreaRandomSampleGenerator();
        BodyArea bodyAreaBack = getBodyAreaRandomSampleGenerator();

        bodyArea.setParent(bodyAreaBack);
        assertThat(bodyArea.getParent()).isEqualTo(bodyAreaBack);

        bodyArea.parent(null);
        assertThat(bodyArea.getParent()).isNull();
    }
}
