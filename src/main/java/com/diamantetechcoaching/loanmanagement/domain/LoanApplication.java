package com.diamantetechcoaching.loanmanagement.domain;

import com.diamantetechcoaching.loanmanagement.LoanApplicationRequest;
import com.diamantetechcoaching.loanmanagement.LoanApplicationResponse;
import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanApplication(String firstName, String lastName, int creditScore, double monthlyIncome, double monthlyDebt,
                       double requestedAmount, String socialSecurityNumber) {

    public LoanStatus determineLoanStatus() {
        if (creditScore() >= 750 && calculateDebtToIncomeRatio() <= 35 && requestedAmount() <= monthlyIncome() * 4) {
            return LoanStatus.APPROVED;
        }
        return LoanStatus.REJECTED;
    }

    private double calculateDebtToIncomeRatio() {
        return (monthlyDebt() / monthlyIncome()) * 100;
    }

    public LoanEntity toLoanEntity(LoanStatus loanStatus) {
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName());
        entity.setLastName(lastName());
        entity.setCreditScore(creditScore());
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio()));
        entity.setApplicationStatus(loanStatus.status());
        entity.setSubmissionTimestamp(LocalDateTime.now());
        return entity;
    }

    public static LoanApplication of(LoanApplicationRequest request, int creditScore) {
        return new LoanApplication(request.getFirstName(),
                request.getLastName(),
                creditScore,
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                request.getSsn());
    }

    public LoanApplicationResponse toLoanApplicationResponse(LoanStatus loanStatus) {
        return new LoanApplicationResponse(
                loanStatus.status(),
                creditScore(),
                monthlyIncome(),
                monthlyDebt(),
                requestedAmount(),
                calculateDebtToIncomeRatio()
        );
    }
}
