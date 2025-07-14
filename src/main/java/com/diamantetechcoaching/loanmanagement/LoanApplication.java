package com.diamantetechcoaching.loanmanagement;

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
}
