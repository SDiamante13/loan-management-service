package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.domain.CreditScore;
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
    void invalidCreditScore() {
        assertThatThrownBy(() -> new CreditScore(299))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreditScore(851))
                .isInstanceOf(IllegalArgumentException.class);
    }
}