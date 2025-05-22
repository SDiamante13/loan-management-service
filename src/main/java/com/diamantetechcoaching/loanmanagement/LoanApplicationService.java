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
        String loanStatus = "Rejected";
        if (meetsLoanAutoApprovalRequirements(request, credit)) {
            loanStatus = "Approved";
        }
        LoanApplicationResponse response = new LoanApplicationResponse(
                loanStatus,
                credit,
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                calculateDebtToIncomeRatio(request)
        );
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setCreditScore(credit);
        entity.setMonthlyIncome(BigDecimal.valueOf(request.getMonthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(request.getMonthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(request.getRequestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio(request)));
        entity.setApplicationStatus(loanStatus);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        saveAction.accept(entity);
        return response;
    }

    private static boolean meetsLoanAutoApprovalRequirements(LoanApplicationRequest request, int credit) {
        return credit >= 750 && calculateDebtToIncomeRatio(request) <= 35 && request.getRequestedAmount() <= request.getMonthlyIncome() * 4;
    }

    private static double calculateDebtToIncomeRatio(LoanApplicationRequest request) {
        return (request.getMonthlyDebt() / request.getMonthlyIncome()) * 100;
    }
}
