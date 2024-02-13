package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.CategoryTestSamples.*;
import static com.estetly.adminpanel.domain.ConcernTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConcernTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concern.class);
        Concern concern1 = getConcernSample1();
        Concern concern2 = new Concern();
        assertThat(concern1).isNotEqualTo(concern2);

        concern2.setId(concern1.getId());
        assertThat(concern1).isEqualTo(concern2);

        concern2 = getConcernSample2();
        assertThat(concern1).isNotEqualTo(concern2);
    }

    @Test
    void categoryTest() throws Exception {
        Concern concern = getConcernRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        concern.setCategory(categoryBack);
        assertThat(concern.getCategory()).isEqualTo(categoryBack);

        concern.category(null);
        assertThat(concern.getCategory()).isNull();
    }
}
