package com.diamantetechcoaching.loanmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DebtToIncomeRatioTest {

    @Test
    void isApproved() {
        DebtToIncomeRatio debtToIncomeRatio = new DebtToIncomeRatio(40000, 150000);

        assertThat(debtToIncomeRatio.value()).isLessThanOrEqualTo(35);
        assertThat(debtToIncomeRatio.isApproved()).isTrue();
    }

    @Test
    void isNotApproved() {
        DebtToIncomeRatio debtToIncomeRatio = new DebtToIncomeRatio(40000, 114000);

        assertThat(debtToIncomeRatio.value()).isGreaterThan(35);
        assertThat(debtToIncomeRatio.isApproved()).isFalse();
    }

    @Test
    void invalidDebtToIncomeRatio() {
        assertThatThrownBy(() -> new DebtToIncomeRatio(-1.0, -1.0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}