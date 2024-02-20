package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.ProcedureTestSamples.*;
import static com.estetly.adminpanel.domain.ReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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

    @Test
    void reviewTest() throws Exception {
        Procedure procedure = getProcedureRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        procedure.addReview(reviewBack);
        assertThat(procedure.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getProcedure()).isEqualTo(procedure);

        procedure.removeReview(reviewBack);
        assertThat(procedure.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getProcedure()).isNull();

        procedure.reviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(procedure.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getProcedure()).isEqualTo(procedure);

        procedure.setReviews(new HashSet<>());
        assertThat(procedure.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getProcedure()).isNull();
    }
}
