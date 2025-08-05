package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record LoanApplication(String firstName, String lastName, int creditScore, double monthlyIncome, double monthlyDebt,
                       double requestedAmount, String ssn) {

    static LoanApplication of(LoanApplicationRequest request, int creditScore) {
        return new LoanApplication(request.getFirstName(),
                request.getLastName(),
                creditScore,
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                request.getSsn());
    }

    LoanApplicationResponse toLoanApplicationResponse(int creditScore, String status) {
        return new LoanApplicationResponse(
                status,
                creditScore,
                monthlyIncome(),
                monthlyDebt(),
                requestedAmount(),
                calculateDebtToIncomeRatio()
        );
    }

    LoanEntity toLoanEntity(int creditScore, String status) {
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName());
        entity.setLastName(lastName());
        entity.setCreditScore(creditScore);
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio()));
        entity.setApplicationStatus(status);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        return entity;
    }

    double calculateDebtToIncomeRatio() {
        return (monthlyDebt() / monthlyIncome()) * 100;
    }
}
