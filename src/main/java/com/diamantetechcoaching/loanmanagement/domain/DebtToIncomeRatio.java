package com.diamantetechcoaching.loanmanagement.domain;

record DebtToIncomeRatio(double monthlyDebt, double monthlyIncome) {

    DebtToIncomeRatio {
        if (monthlyDebt < 0.0 || monthlyIncome < 0.0) {
            throw new IllegalArgumentException("Monthly debt and income must be positive values.");
        }
    }

    boolean isApproved() {
        return value() <= 35;
    }

    double value() {
        return (monthlyDebt / monthlyIncome) * 100;
    }
}
