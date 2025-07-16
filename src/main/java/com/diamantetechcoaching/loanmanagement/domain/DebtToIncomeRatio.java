package com.diamantetechcoaching.loanmanagement.domain;

public record DebtToIncomeRatio(double monthlyDebt, double monthlyIncome) {

    public DebtToIncomeRatio {
        if (monthlyDebt < 0.0 || monthlyIncome < 0.0) {
            throw new IllegalArgumentException("Monthly debt and income must be positive values.");
        }
    }

    public boolean isApproved() {
        return value() < 36;
    }

    public double value() {
        return (monthlyDebt() / monthlyIncome()) * 100;
    }

    public boolean needsManualReview() {
        return value() >= 36 && value() <= 50;
    }
}

