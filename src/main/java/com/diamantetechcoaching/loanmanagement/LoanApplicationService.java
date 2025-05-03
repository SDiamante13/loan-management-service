package com.diamantetechcoaching.loanmanagement;

import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService {

    public LoanApplicationResponse processLoanApplication(LoanApplicationRequest request) {
        String ssn = request.getSSN();
        int credit = AlmanacService.fetchCreditScore(ssn);
        double income = request.getMonthlyIncome();
        double debt = request.getMonthlyDebt();
        double loanAmount = request.getRequestedAmount();

        double dti = (debt / income) * 100;
        boolean creditOk = credit >= 750;
        boolean dtiOk = dti <= 35;
        boolean amountOk = loanAmount <= income * 4;

        String status = "Rejected";
        if (creditOk && dtiOk && amountOk) {
            status = "Approved";
        }

        LoanApplicationResponse response = new LoanApplicationResponse(
                status,
                credit,
                income,
                debt,
                loanAmount,
                dti
        );
        return response;
    }
}
