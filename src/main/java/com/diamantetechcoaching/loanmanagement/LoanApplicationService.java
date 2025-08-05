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
        int creditScore = AlmanacService.getInstance().fetchCreditScore(ssn);
        return processLoanApplication(request, creditScore, entity -> {
            try {
                loanApplicationRepository.save(entity);
            } catch (Exception e) {
                log.error("Failed to persist loan application: {}", e.getMessage());
                throw new RuntimeException("Database error", e);
            }
        });
    }

    LoanApplicationResponse processLoanApplication(LoanApplicationRequest request, int creditScore, Consumer<LoanEntity> saveToDatabase) {
        LoanApplication loanApplication = LoanApplication.of(request, creditScore);
        String status = "Rejected";
        if (creditScore >= 750 && calculateDebtToIncomeRatio(loanApplication) <= 35 && loanApplication.requestedAmount() <= loanApplication.monthlyIncome() * 4) {
            status = "Approved";
        }

        LoanApplicationResponse response = new LoanApplicationResponse(
                status,
                creditScore,
                loanApplication.monthlyIncome(),
                loanApplication.monthlyDebt(),
                loanApplication.requestedAmount(),
                calculateDebtToIncomeRatio(loanApplication)
        );

        LoanEntity entity = new LoanEntity();
        entity.setFirstName(loanApplication.firstName());
        entity.setLastName(loanApplication.lastName());
        entity.setCreditScore(creditScore);
        entity.setMonthlyIncome(BigDecimal.valueOf(loanApplication.monthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(loanApplication.monthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(loanApplication.requestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio(loanApplication)));
        entity.setApplicationStatus(status);
        entity.setSubmissionTimestamp(LocalDateTime.now());

        saveToDatabase.accept(entity);

        return response;
    }

    private static double calculateDebtToIncomeRatio(LoanApplication loanApplication) {
        return (loanApplication.monthlyDebt() / loanApplication.monthlyIncome()) * 100;
    }
}
