package com.diamantetechcoaching.loanmanagement.domain;

import com.diamantetechcoaching.loanmanagement.LoanApplicationRequest;
import com.diamantetechcoaching.loanmanagement.LoanApplicationResponse;
import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoanApplicationTest {

    @Test
    void rejectedLoans() {
        LoanApplication lowCreditScore = LoanApplication.of(new LoanApplicationRequest(
                "first",
                "last",
                5_000,
                1_500,
                18_000,
                "999"), 599
        );
        LoanApplication highDebtToIncomeRatio = LoanApplication.of(new LoanApplicationRequest(
                "first",
                "last",
                5_000,
                10_000,
                18_000,
                "999"), 750
        );
        LoanApplication loanAmountTooHigh = LoanApplication.of(new LoanApplicationRequest(
                "first",
                "last",
                5_000,
                1_500,
                200_000,
                "999"), 750
        );

        assertThat(lowCreditScore.determineLoanStatus()).isEqualTo(LoanStatus.REJECTED);
        assertThat(highDebtToIncomeRatio.determineLoanStatus()).isEqualTo(LoanStatus.REJECTED);
        assertThat(loanAmountTooHigh.determineLoanStatus()).isEqualTo(LoanStatus.REJECTED);
    }

    @Test
    void needsManualReviewForLoan() {
        LoanApplication lowCreditScore = LoanApplication.of(new LoanApplicationRequest(
                "Bruce",
                "Banner",
                5_000,
                1_500,
                18_000,
                "999"), 700
        );
        LoanApplication highDebtToIncomeRatio = LoanApplication.of(new LoanApplicationRequest(
                "Bruce",
                "Banner",
                4_000,
                1_500,
                18_000,
                "999"), 750
        );

        LoanApplication loanAmountTooHigh = LoanApplication.of(new LoanApplicationRequest(
                "Bruce",
                "Banner",
                5_000,
                1_500,
                50_000,
                "999"), 750
        );

        assertThat(lowCreditScore.determineLoanStatus()).isEqualTo(LoanStatus.NEEDS_REVIEW);
        assertThat(highDebtToIncomeRatio.determineLoanStatus()).isEqualTo(LoanStatus.NEEDS_REVIEW);
        assertThat(loanAmountTooHigh.determineLoanStatus()).isEqualTo(LoanStatus.NEEDS_REVIEW);
    }

    @Test
    void approvedLoans() {
        LoanApplication approvedLoan = LoanApplication.of(new LoanApplicationRequest(
                "Bruce",
                "Banner",
                5_000,
                1_500,
                18_000,
                "999"), 750
        );

        assertThat(approvedLoan.determineLoanStatus()).isEqualTo(LoanStatus.APPROVED);
    }

    @Test
    void mapsToLoanEntity() {
        LoanApplication loan = new LoanApplication(
                "Bruce",
                "Banner",
                new CreditScore(750),
                5_000,
                1_500,
                18_000,
                "999"
        );
        LoanEntity expectedLoanEntity = new LoanEntity(
                "Bruce",
                "Banner",
                750,
                new BigDecimal("5000.0"),
                new BigDecimal("1500.0"),
                new BigDecimal("18000.0"),
                new BigDecimal("30.0"),
                "Approved",
                null);

        LoanEntity loanEntity = loan.toLoanEntity(LoanStatus.APPROVED);

        assertThat(loanEntity)
                .usingRecursiveComparison()
                .ignoringFields("submissionTimestamp")
                .isEqualTo(expectedLoanEntity);
    }

    @Test
    void mapsToLoanApplicationResponse() {
        LoanApplication loan = new LoanApplication(
                "Bruce",
                "Banner",
                new CreditScore(750),
                5_000,
                1_500,
                18_000,
                "999"
        );
        LoanApplicationResponse expectedResponse = new LoanApplicationResponse(
                "Approved",
                750,
                5000.0,
                1500.0,
                18000.0,
                30.0
        );

        LoanApplicationResponse loanApplicationResponse = loan.toLoanApplicationResponse(LoanStatus.APPROVED);

        assertThat(loanApplicationResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }
}