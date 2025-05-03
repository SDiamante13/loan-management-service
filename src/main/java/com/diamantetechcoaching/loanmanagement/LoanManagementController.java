package com.diamantetechcoaching.loanmanagement;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/loan")
public class LoanManagementController {

    private static final List<LoanApplicationResponse> applications = new ArrayList<>();

    @PostMapping("/apply")
    public LoanApplicationResponse applyForLoan(@RequestBody LoanApplicationRequest request) {
        int credit = request.getCreditScore();
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

        applications.add(response);
        return response;
    }

    @GetMapping("/all")
    public List<LoanApplicationResponse> getAllApplications() {
        return applications;
    }
}
