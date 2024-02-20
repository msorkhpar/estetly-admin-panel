package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.ProcedureTestSamples.*;
import static com.estetly.adminpanel.domain.ReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = getReviewSample1();
        Review review2 = new Review();
        assertThat(review1).isNotEqualTo(review2);

        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);

        review2 = getReviewSample2();
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    void procedureTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Procedure procedureBack = getProcedureRandomSampleGenerator();

        review.setProcedure(procedureBack);
        assertThat(review.getProcedure()).isEqualTo(procedureBack);

        review.procedure(null);
        assertThat(review.getProcedure()).isNull();
    }
}
