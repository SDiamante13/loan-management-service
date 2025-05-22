package com.diamantetechcoaching.loanmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoanApplicationServiceTest {

    @Autowired
    LoanApplicationService loanApplicationService;

    @Test
    void processLoanApplication() {
        LoanApplicationRequest request = new LoanApplicationRequest(
                "first",
                "last",
                10000,
                1000,
                30000,
                "123");

        LoanApplicationResponse response = loanApplicationService.processLoanApplication(request, 750, entity1 -> {});

        assertThat(response.getStatus()).isEqualTo("Approved");
    }
}