package com.diamantetechcoaching.loanmanagement.domain;

record LoanToIncomeRatio(double loanAmount, double monthlyIncome) {

    LoanToIncomeRatio {
        if (loanAmount < 0 || monthlyIncome <= 0) {
            throw new IllegalArgumentException("Loan amount cannot be negative and monthly income must be positive");
        }
    }

    boolean isApproved() {
        return value() <= 4;
    }

    double value() {
        return loanAmount / monthlyIncome;
    }
}
