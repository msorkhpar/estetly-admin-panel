package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.CategoryTestSamples.*;
import static com.estetly.adminpanel.domain.ConcernTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void concernTest() throws Exception {
        Category category = getCategoryRandomSampleGenerator();
        Concern concernBack = getConcernRandomSampleGenerator();

        category.addConcern(concernBack);
        assertThat(category.getConcerns()).containsOnly(concernBack);
        assertThat(concernBack.getCategory()).isEqualTo(category);

        category.removeConcern(concernBack);
        assertThat(category.getConcerns()).doesNotContain(concernBack);
        assertThat(concernBack.getCategory()).isNull();

        category.concerns(new HashSet<>(Set.of(concernBack)));
        assertThat(category.getConcerns()).containsOnly(concernBack);
        assertThat(concernBack.getCategory()).isEqualTo(category);

        category.setConcerns(new HashSet<>());
        assertThat(category.getConcerns()).doesNotContain(concernBack);
        assertThat(concernBack.getCategory()).isNull();
    }
}
