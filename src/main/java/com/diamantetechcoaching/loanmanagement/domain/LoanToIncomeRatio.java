package com.diamantetechcoaching.loanmanagement.domain;

public record LoanToIncomeRatio(double loanAmount, double monthlyIncome) {

    public LoanToIncomeRatio {
        if (loanAmount < 0 || monthlyIncome <= 0) {
            throw new IllegalArgumentException("Loan amount cannot be negative and monthly income must be positive");
        }
    }

    public boolean isApproved() {
        return value() <= 4;
    }

    public double value() {
        return loanAmount() / monthlyIncome();
    }
}
