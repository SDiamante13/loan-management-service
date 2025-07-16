package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.domain.LoanToIncomeRatio;
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
    void needsManualReview() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(150000, 30000);

        assertThat(loanToIncomeRatio.value()).isBetween(4.0, 10.0);
        assertThat(loanToIncomeRatio.needsManualReview()).isTrue();
    }

    @Test
    void isNotApproved() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(500000, 30000);

        assertThat(loanToIncomeRatio.value()).isGreaterThan(10.0);
        assertThat(loanToIncomeRatio.isApproved()).isFalse();
        assertThat(loanToIncomeRatio.needsManualReview()).isFalse();
    }

    @Test
    void invalidLoanToIncomeRatio() {
        assertThatThrownBy(() -> new LoanToIncomeRatio(-1.0, -1.0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
