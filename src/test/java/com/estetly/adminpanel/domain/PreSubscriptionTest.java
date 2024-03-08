package com.estetly.adminpanel.domain;

import static com.estetly.adminpanel.domain.PreSubscriptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.estetly.adminpanel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreSubscriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreSubscription.class);
        PreSubscription preSubscription1 = getPreSubscriptionSample1();
        PreSubscription preSubscription2 = new PreSubscription();
        assertThat(preSubscription1).isNotEqualTo(preSubscription2);

        preSubscription2.setId(preSubscription1.getId());
        assertThat(preSubscription1).isEqualTo(preSubscription2);

        preSubscription2 = getPreSubscriptionSample2();
        assertThat(preSubscription1).isNotEqualTo(preSubscription2);
    }
}
