package com.diamantetechcoaching.loanmanagement.domain;

import com.diamantetechcoaching.loanmanagement.LoanApplicationRequest;
import com.diamantetechcoaching.loanmanagement.LoanApplicationResponse;
import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanApplication(String firstName, String lastName, double monthlyIncome, double monthlyDebt,
                              double requestedAmount, String ssn, CreditScore creditScore) {

    public static LoanApplication of(LoanApplicationRequest request, int creditScore) {
        return new LoanApplication(
                request.getFirstName(),
                request.getLastName(),
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                request.getSsn(),
                new CreditScore(creditScore)
        );
    }

    public LoanStatus determineLoanStatus() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(requestedAmount, monthlyIncome);
        if (creditScore.isApproved() && debtToIncomeRatio().isApproved() && loanToIncomeRatio.isApproved()) {
            return LoanStatus.APPROVED;
        }
        if (creditScore().needsManualReview() || debtToIncomeRatio().needsManualReview() || loanToIncomeRatio.needsManualReview()) {
            return LoanStatus.NEEDS_MANUAL_REVIEW;
        }
        return LoanStatus.REJECTED;
    }

    private DebtToIncomeRatio debtToIncomeRatio() {
        return new DebtToIncomeRatio(monthlyDebt, monthlyIncome);
    }

    public LoanEntity toLoanEntity(int creditScore, LoanStatus loanStatus) {
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName());
        entity.setLastName(lastName());
        entity.setCreditScore(creditScore);
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(debtToIncomeRatio().value()));
        entity.setApplicationStatus(loanStatus.status());
        entity.setSubmissionTimestamp(LocalDateTime.now());
        return entity;
    }

    public LoanApplicationResponse toLoanApplicationResponse(int creditScore, LoanStatus loanStatus) {
        return new LoanApplicationResponse(
                loanStatus.status(),
                creditScore,
                monthlyIncome(),
                monthlyDebt(),
                requestedAmount(),
                debtToIncomeRatio().value()
        );
    }
}
