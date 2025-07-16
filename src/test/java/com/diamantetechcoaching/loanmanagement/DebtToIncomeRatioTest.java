package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.domain.DebtToIncomeRatio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DebtToIncomeRatioTest {

    @Test
    void isApproved() {
        DebtToIncomeRatio debtToIncomeRatio = new DebtToIncomeRatio(40000, 150000);

        assertThat(debtToIncomeRatio.value()).isLessThan(36);
        assertThat(debtToIncomeRatio.isApproved()).isTrue();
    }

    @Test
    void needsManualReview() {
        DebtToIncomeRatio debtToIncomeRatio = new DebtToIncomeRatio(40000, 100000);

        assertThat(debtToIncomeRatio.value())
                .isBetween(36.0, 50.0);

        assertThat(debtToIncomeRatio.needsManualReview()).isTrue();
    }

    @Test
    void isNotApproved() {
        DebtToIncomeRatio debtToIncomeRatio = new DebtToIncomeRatio(40000, 79000);

        assertThat(debtToIncomeRatio.value()).isGreaterThan(50);
        assertThat(debtToIncomeRatio.isApproved()).isFalse();
        assertThat(debtToIncomeRatio.needsManualReview()).isFalse();
    }

    @Test
    void invalidDebtToIncomeRatio() {
        assertThatThrownBy(() -> new DebtToIncomeRatio(-1.0, -1.0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}