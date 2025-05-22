package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;
import com.diamantetechcoaching.loanmanagement.repository.LoanApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
public class LoanApplicationService {

    private static final Logger log = LoggerFactory.getLogger(LoanApplicationService.class);

    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public LoanApplicationResponse processLoanApplication(LoanApplicationRequest request) {
        String ssn = request.getSsn();
        int credit = AlmanacService.fetchCreditScore(ssn);

        return processLoanApplication(request, credit, entity1 -> {
            try {
                loanApplicationRepository.save(entity1);
            } catch (Exception e) {
                log.error("Failed to persist loan application: {}", e.getMessage());
                throw new RuntimeException("Database error", e);
            }
        });
    }

    LoanApplicationResponse processLoanApplication(LoanApplicationRequest request, int credit, Consumer<LoanEntity> saveAction) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        double monthlyIncome = request.getMonthlyIncome();
        double monthlyDebt = request.getMonthlyDebt();
        double loanAmount = request.getRequestedAmount();
        double debtToIncomeRatio = (monthlyDebt / monthlyIncome) * 100;
        String loanStatus = "Rejected";
        if (credit >= 750 && debtToIncomeRatio <= 35 && loanAmount <= monthlyIncome * 4) {
            loanStatus = "Approved";
        }
        LoanApplicationResponse response = new LoanApplicationResponse(
                loanStatus,
                credit,
                monthlyIncome,
                monthlyDebt,
                loanAmount,
                debtToIncomeRatio
        );
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setCreditScore(credit);
        entity.setMonthlyIncome(BigDecimal.valueOf(monthlyIncome));
        entity.setMonthlyDebt(BigDecimal.valueOf(monthlyDebt));
        entity.setRequestedAmount(BigDecimal.valueOf(loanAmount));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(debtToIncomeRatio));
        entity.setApplicationStatus(loanStatus);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        saveAction.accept(entity);
        return response;
    }
}
