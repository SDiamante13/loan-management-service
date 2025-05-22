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

    private record LoanApplication(String firstName, String lastName, int creditScore, double monthlyIncome, double monthlyDebt,
                                   double requestedAmount, String socialSecurityNumber) {

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

    LoanApplicationResponse processLoanApplication(LoanApplicationRequest request, int credit, Consumer<LoanEntity> saveAction) {
        LoanApplication loanApplication = LoanApplication.of(request, credit);

        String loanStatus = "Rejected";
        if (credit >= 750 && calculateDebtToIncomeRatio(loanApplication) <= 35 && loanApplication.requestedAmount() <= loanApplication.monthlyIncome() * 4) {
            loanStatus = "Approved";
        }

        LoanApplicationResponse response = new LoanApplicationResponse(
                loanStatus,
                credit,
                request.getMonthlyIncome(),
                request.getMonthlyDebt(),
                request.getRequestedAmount(),
                calculateDebtToIncomeRatio(loanApplication)
        );
        LoanEntity entity = new LoanEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setCreditScore(credit);
        entity.setMonthlyIncome(BigDecimal.valueOf(request.getMonthlyIncome()));
        entity.setMonthlyDebt(BigDecimal.valueOf(request.getMonthlyDebt()));
        entity.setRequestedAmount(BigDecimal.valueOf(request.getRequestedAmount()));
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(calculateDebtToIncomeRatio(loanApplication)));
        entity.setApplicationStatus(loanStatus);
        entity.setSubmissionTimestamp(LocalDateTime.now());
        saveAction.accept(entity);
        return response;
    }

    private static double calculateDebtToIncomeRatio(LoanApplication loanApplication) {
        return (loanApplication.monthlyDebt() / loanApplication.monthlyIncome()) * 100;
    }
}
