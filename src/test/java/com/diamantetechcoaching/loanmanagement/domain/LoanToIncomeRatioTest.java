package com.diamantetechcoaching.loanmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoanToIncomeRatioTest {

    @Test
    void isApproved() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(100000, 30000);

        assertThat(loanToIncomeRatio.value()).isLessThanOrEqualTo(4.0);
        assertThat(loanToIncomeRatio.isApproved()).isTrue();
    }

    @Test
    void isNotApproved() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(500000, 30000);

        assertThat(loanToIncomeRatio.value()).isGreaterThan(4.0);
        assertThat(loanToIncomeRatio.isApproved()).isFalse();
    }

    @Test
    void needsManualReview() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(130000, 30000);

        assertThat(loanToIncomeRatio.value()).isStrictlyBetween(4.01, 10.01);
        assertThat(loanToIncomeRatio.needsManualReview()).isTrue();
    }

    @Test
    void invalidLoanToIncomeRatio() {
        assertThatThrownBy(() -> new LoanToIncomeRatio(-1.0, -1.0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}