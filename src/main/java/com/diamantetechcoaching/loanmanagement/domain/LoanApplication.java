package com.diamantetechcoaching.loanmanagement.domain;

import com.diamantetechcoaching.loanmanagement.LoanApplicationRequest;
import com.diamantetechcoaching.loanmanagement.LoanApplicationResponse;
import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanApplication(String firstName, String lastName, CreditScore creditScore, double monthlyIncome, double monthlyDebt,
                              double requestedAmount, String socialSecurityNumber) {

    public LoanStatus determineLoanStatus() {
        LoanToIncomeRatio loanToIncomeRatio = new LoanToIncomeRatio(requestedAmount, monthlyIncome);
        if (creditScore.isApproved() && debtToIncomeRatio().isApproved() && loanToIncomeRatio.isApproved()) {
            return LoanStatus.APPROVED;
        }
        return LoanStatus.REJECTED;
    }

    private DebtToIncomeRatio debtToIncomeRatio() {
        return new DebtToIncomeRatio(monthlyDebt, monthlyIncome);
    }

    public LoanEntity toLoanEntity(LoanStatus loanStatus) {
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName());
        entity.setLastName(lastName());
        entity.setCreditScore(creditScore.value());
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(debtToIncomeRatio().value()));
        entity.setApplicationStatus(loanStatus.status());
        entity.setSubmissionTimestamp(LocalDateTime.now());
        return entity;
    }

    public static LoanApplication of(LoanApplicationRequest request, int creditScore) {
        return new LoanApplication(request.getFirstName(),
                request.getLastName(),
                new CreditScore(creditScore),
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                request.getSsn());
    }

    public LoanApplicationResponse toLoanApplicationResponse(LoanStatus loanStatus) {
        return new LoanApplicationResponse(
                loanStatus.status(),
                creditScore().value(),
                monthlyIncome(),
                monthlyDebt(),
                requestedAmount(),
                debtToIncomeRatio().value()
        );
    }
}
