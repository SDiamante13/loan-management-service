package com.diamantetechcoaching.loanmanagement;

public class LoanApplicationRequest {
    private int creditScore;
    private double monthlyIncome;
    private double monthlyDebt;
    private double requestedAmount;
    private String ssn;

    // Default constructor for deserialization
    public LoanApplicationRequest() {
    }

    public LoanApplicationRequest(int creditScore, double monthlyIncome, double monthlyDebt, double requestedAmount) {
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.monthlyDebt = monthlyDebt;
        this.requestedAmount = requestedAmount;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getMonthlyDebt() {
        return monthlyDebt;
    }

    public void setMonthlyDebt(double monthlyDebt) {
        this.monthlyDebt = monthlyDebt;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getSSN() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}

