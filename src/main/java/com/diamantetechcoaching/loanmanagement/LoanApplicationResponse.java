package com.diamantetechcoaching.loanmanagement;

public class LoanApplicationResponse {
    private String status;
    private int creditScore;
    private double monthlyIncome;
    private double monthlyDebt;
    private double requestedAmount;
    private double dti;

    // Default constructor
    public LoanApplicationResponse() {
    }

    public LoanApplicationResponse(String status, int creditScore, double monthlyIncome, 
                                   double monthlyDebt, double requestedAmount, double dti) {
        this.status = status;
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.monthlyDebt = monthlyDebt;
        this.requestedAmount = requestedAmount;
        this.dti = dti;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getDti() {
        return dti;
    }

    public void setDti(double dti) {
        this.dti = dti;
    }
}

