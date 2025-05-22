package com.diamantetechcoaching.loanmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoanApplicationTest {

    @Test
    void rejectedLoans() {
        LoanApplication lowCreditScore = new LoanApplication(
                "first",
                "last",
                new CreditScore(599),
                5_000,
                1_500,
                18_000,
                "999"
        );
        LoanApplication highDebtToIncomeRatio = new LoanApplication(
                "first",
                "last",
                new CreditScore(750),
                5_000,
                10_000,
                18000,
                "999"
        );
        LoanApplication loanAmountTooHigh = new LoanApplication(
                "first",
                "last",
                new CreditScore(750),
                5_000,
                1_500,
                200_000,
                "999"
        );

        assertThat(lowCreditScore.determineLoanStatus()).isEqualTo(LoanStatus.REJECTED);
        assertThat(highDebtToIncomeRatio.determineLoanStatus()).isEqualTo(LoanStatus.REJECTED);
        assertThat(loanAmountTooHigh.determineLoanStatus()).isEqualTo(LoanStatus.REJECTED);
    }

    @Test
    void manualReviewLoans() {
        LoanApplication lowCreditScore = new LoanApplication(
                "Bruce",
                "Banner",
                new CreditScore(601),
                5_000,
                1_500,
                18_000,
                "999"
        );
        LoanApplication highDebtToIncomeRatio = new LoanApplication(
                "Bruce",
                "Banner",
                new CreditScore(750),
                4_000,
                1_500,
                18_000,
                "999"
        );
        LoanApplication loanAmountTooHigh = new LoanApplication(
                "first",
                "last",
                new CreditScore(750),
                5_000,
                1_500,
                45_000,
                "999"
        );

        assertThat(lowCreditScore.determineLoanStatus()).isEqualTo(LoanStatus.NEEDS_MANUAL_REVIEW);
        assertThat(highDebtToIncomeRatio.determineLoanStatus()).isEqualTo(LoanStatus.NEEDS_MANUAL_REVIEW);
        assertThat(loanAmountTooHigh.determineLoanStatus()).isEqualTo(LoanStatus.NEEDS_MANUAL_REVIEW);
    }

    @Test
    void approvedLoans() {
        LoanApplication approvedLoan = new LoanApplication(
                "Bruce",
                "Banner",
                new CreditScore(750),
                5_000,
                1_500,
                18_000,
                "999"
        );

        assertThat(approvedLoan.determineLoanStatus()).isEqualTo(LoanStatus.APPROVED);
    }
}