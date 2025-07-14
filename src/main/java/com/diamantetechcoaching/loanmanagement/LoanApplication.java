package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanApplication(String firstName, String lastName, double monthlyIncome, double monthlyDebt,
                              double requestedAmount, String ssn, int creditScore) {

    public static LoanApplication of(LoanApplicationRequest request, int creditScore) {
        return new LoanApplication(
                request.getFirstName(),
                request.getLastName(),
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                request.getSsn(),
                creditScore
        );
    }

    String determineLoanStatus(int creditScore) {
        if (creditScore >= 750 &&
                calculateDebtToIncomeRatio() <= 35 &&
                requestedAmount() <= monthlyIncome() * 4) {
            return "Approved";
        }
        return "Rejected";
    }

    double calculateDebtToIncomeRatio() {
        return (monthlyDebt() / monthlyIncome()) * 100;
    }

    LoanEntity toLoanEntity(int creditScore, String loanStatus) {
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName());
        entity.setLastName(lastName());
        entity.setCreditScore(creditScore);
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio()));
        entity.setApplicationStatus(loanStatus);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        return entity;
    }

    LoanApplicationResponse toLoanApplicationResponse(int creditScore, String loanStatus) {
        return new LoanApplicationResponse(
                loanStatus,
                creditScore,
                monthlyIncome(),
                monthlyDebt(),
                requestedAmount(),
                calculateDebtToIncomeRatio()
        );
    }
}
