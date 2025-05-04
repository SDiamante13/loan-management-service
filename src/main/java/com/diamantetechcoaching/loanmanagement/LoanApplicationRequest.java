package com.diamantetechcoaching.loanmanagement;

public class LoanApplicationRequest {

    private String firstName;
    private String lastName;
    private double monthlyIncome;
    private double monthlyDebt;
    private double requestedAmount;
    private String ssn;

    public LoanApplicationRequest() {
    }

    public LoanApplicationRequest(String firstName, String lastName, double monthlyIncome, double monthlyDebt, double requestedAmount, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.monthlyIncome = monthlyIncome;
        this.monthlyDebt = monthlyDebt;
        this.requestedAmount = requestedAmount;
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}

