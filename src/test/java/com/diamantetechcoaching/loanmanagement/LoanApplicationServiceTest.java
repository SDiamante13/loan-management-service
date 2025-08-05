package com.diamantetechcoaching.loanmanagement;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoanApplicationServiceTest {

    @Autowired
    LoanApplicationService loanApplicationService;

    @Test
    void rejectedLoans() {
        LoanApplicationResponse lowCreditScore = loanApplicationService.processLoanApplication(new LoanApplicationRequest(
                "Bruce",
                "Banner",
                5_000,
                1_500,
                18_000,
                "999"
        ), 700, loanEntity -> {
        });

        Approvals.verify(lowCreditScore);
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