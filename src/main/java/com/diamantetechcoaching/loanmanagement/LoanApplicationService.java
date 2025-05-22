package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;
import com.diamantetechcoaching.loanmanagement.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationResponse processLoanApplication(LoanApplicationRequest request) {
        String ssn = request.getSsn();
        int credit = AlmanacService.fetchCreditScore(ssn);

        return processLoanApplication(request, credit, entity1 -> {
            try {
                loanApplicationRepository.save(entity1);
            } catch (Exception e) {
                System.err.println("Failed to persist loan application: " + e.getMessage());
                throw new RuntimeException("Database error", e);
            }
        });
    }

    LoanApplicationResponse processLoanApplication(LoanApplicationRequest request, int credit, Consumer<LoanEntity> saveAction) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
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
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setCreditScore(credit);
        entity.setMonthlyIncome(BigDecimal.valueOf(income));
        entity.setMonthlyDebt(BigDecimal.valueOf(debt));
        entity.setRequestedAmount(BigDecimal.valueOf(loanAmount));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(dti));
        entity.setApplicationStatus(status);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        saveAction.accept(entity);
        return response;
    }
}
