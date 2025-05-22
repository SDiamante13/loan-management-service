package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record LoanApplication(String firstName, String lastName, int creditScore, double monthlyIncome, double monthlyDebt,
                       double requestedAmount, String socialSecurityNumber) {

    LoanEntity toLoanEntity(String loanStatus) {
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName());
        entity.setLastName(lastName());
        entity.setCreditScore(creditScore());
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio()));
        entity.setApplicationStatus(loanStatus);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        return entity;
    }

    double calculateDebtToIncomeRatio() {
        return (monthlyDebt() / monthlyIncome()) * 100;
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

    public LoanApplicationResponse toLoanApplicationResponse(String loanStatus) {
        return new LoanApplicationResponse(
                loanStatus,
                creditScore(),
                monthlyIncome(),
                monthlyDebt(),
                requestedAmount(),
                calculateDebtToIncomeRatio()
        );
    }
}
