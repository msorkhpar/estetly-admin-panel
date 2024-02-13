package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.BodyAreaConcernAssociationTestSamples.*;
import static com.estetly.adminpanel.domain.BodyAreaTestSamples.*;
import static com.estetly.adminpanel.domain.ConcernTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BodyAreaConcernAssociationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyAreaConcernAssociation.class);
        BodyAreaConcernAssociation bodyAreaConcernAssociation1 = getBodyAreaConcernAssociationSample1();
        BodyAreaConcernAssociation bodyAreaConcernAssociation2 = new BodyAreaConcernAssociation();
        assertThat(bodyAreaConcernAssociation1).isNotEqualTo(bodyAreaConcernAssociation2);

        bodyAreaConcernAssociation2.setId(bodyAreaConcernAssociation1.getId());
        assertThat(bodyAreaConcernAssociation1).isEqualTo(bodyAreaConcernAssociation2);

        bodyAreaConcernAssociation2 = getBodyAreaConcernAssociationSample2();
        assertThat(bodyAreaConcernAssociation1).isNotEqualTo(bodyAreaConcernAssociation2);
    }

    @Test
    void bodyAreaTest() throws Exception {
        BodyAreaConcernAssociation bodyAreaConcernAssociation = getBodyAreaConcernAssociationRandomSampleGenerator();
        BodyArea bodyAreaBack = getBodyAreaRandomSampleGenerator();

        bodyAreaConcernAssociation.setBodyArea(bodyAreaBack);
        assertThat(bodyAreaConcernAssociation.getBodyArea()).isEqualTo(bodyAreaBack);

        bodyAreaConcernAssociation.bodyArea(null);
        assertThat(bodyAreaConcernAssociation.getBodyArea()).isNull();
    }

    @Test
    void concernTest() throws Exception {
        BodyAreaConcernAssociation bodyAreaConcernAssociation = getBodyAreaConcernAssociationRandomSampleGenerator();
        Concern concernBack = getConcernRandomSampleGenerator();

        bodyAreaConcernAssociation.setConcern(concernBack);
        assertThat(bodyAreaConcernAssociation.getConcern()).isEqualTo(concernBack);

        bodyAreaConcernAssociation.concern(null);
        assertThat(bodyAreaConcernAssociation.getConcern()).isNull();
    }
}
