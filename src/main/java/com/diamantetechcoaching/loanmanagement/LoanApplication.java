package com.diamantetechcoaching.loanmanagement;

record LoanApplication(String firstName, String lastName, int creditScore, double monthlyIncome, double monthlyDebt,
                       double requestedAmount, String ssn) {

    public static LoanApplication of(LoanApplicationRequest request, int creditScore) {
        return new LoanApplication(request.getFirstName(),
                request.getLastName(),
                creditScore,
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                request.getSsn());
    }
}
