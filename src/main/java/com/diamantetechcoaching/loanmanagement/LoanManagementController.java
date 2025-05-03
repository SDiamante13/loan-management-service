package com.diamantetechcoaching.loanmanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/loan")
public class LoanManagementController {

    private static final List<LoanApplicationResponse> applications = new ArrayList<>();

    @PostMapping("/apply")
    public LoanApplicationResponse applyForLoan(@RequestBody LoanApplicationRequest request) {
        String ssn = request.getSSN();
        int credit = fetchCreditScore(ssn);
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

    private int fetchCreditScore(String ssn) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            CreditScoreRequest creditScoreRequest = new CreditScoreRequest(ssn);
            ResponseEntity<CreditScoreResponse> creditScoreResponse = restTemplate.postForEntity("http://localhost:8080/creditscore", creditScoreRequest, CreditScoreResponse.class);
            return creditScoreResponse.getBody().getCreditScore();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // fail closed
        }
    }

    @GetMapping("/all")
    public List<LoanApplicationResponse> getAllApplications() {
        return applications;
    }
}
