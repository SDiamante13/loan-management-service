package com.diamantetechcoaching.loanmanagement;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class LoanApplicationServiceTest {

    private final LoanApplicationService loanApplicationService = new LoanApplicationService(null);

    @Test
    void rejectedLoans() {
        LoanApplicationResponse lowCreditScore = loanApplicationService.processLoanApplication(new LoanApplicationRequest(
                "first",
                "last",
                5_000,
                1_500,
                18_000,
                "999"
        ), 700, loanEntity -> {
        });
        LoanApplicationResponse highDebtToIncomeRatio = loanApplicationService.processLoanApplication(new LoanApplicationRequest(
                "first",
                "last",
                5_000,
                10_000,
                18000,
                "999"
        ), 750, loanEntity -> {
        });
        LoanApplicationResponse loanAmountTooHigh = loanApplicationService.processLoanApplication(new LoanApplicationRequest(
                "first",
                "last",
                5_000,
                1_500,
                200_000,
                "999"
        ), 750, loanEntity -> {
        });

        Approvals.verifyAll("Rejected Loans",
                new LoanApplicationResponse[]{
                        lowCreditScore,
                        highDebtToIncomeRatio,
                        loanAmountTooHigh
                });
    }

    @Test
    void approvedLoans() {
        LoanApplicationResponse approvedLoan = loanApplicationService.processLoanApplication(new LoanApplicationRequest(
                "Bruce",
                "Banner",
                5_000,
                1_500,
                18_000,
                "999"
        ), 750, loanEntity -> {
        });

        Approvals.verify(approvedLoan);
    }
}