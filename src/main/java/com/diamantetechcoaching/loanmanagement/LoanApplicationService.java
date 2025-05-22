package com.diamantetechcoaching.loanmanagement;

import com.diamantetechcoaching.loanmanagement.domain.LoanApplication;
import com.diamantetechcoaching.loanmanagement.domain.LoanStatus;
import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;
import com.diamantetechcoaching.loanmanagement.repository.LoanApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        LoanApplication loanApplication = LoanApplication.of(request, credit);

        LoanStatus loanStatus = loanApplication.determineLoanStatus();

        LoanEntity entity = loanApplication.toLoanEntity(loanStatus);
        saveAction.accept(entity);
        return loanApplication.toLoanApplicationResponse(loanStatus);
    }

}
