package com.diamantetechcoaching.loanmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreditScoreTest {

    @Test
    void isApproved() {
        CreditScore creditScore = new CreditScore(750);

        assertThat(creditScore.isApproved()).isTrue();
    }

    @Test
    void isNotApproved() {
        CreditScore creditScore = new CreditScore(749);

        assertThat(creditScore.isApproved()).isFalse();
    }

    @Test
    void needsManualReview() {
        assertThat(new CreditScore(600).needsReview()).isTrue();
        assertThat(new CreditScore(749).needsReview()).isTrue();
    }

    @Test
    void invalidCreditScore() {
        assertThatThrownBy(() -> new CreditScore(299))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreditScore(851))
                .isInstanceOf(IllegalArgumentException.class);
    }
}