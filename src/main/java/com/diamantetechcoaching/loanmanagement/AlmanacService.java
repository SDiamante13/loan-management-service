package com.diamantetechcoaching.loanmanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AlmanacService {

    public static int fetchCreditScore(String ssn) {
        RestTemplate restTemplate = new RestTemplate();
        CreditScoreRequest creditScoreRequest = new CreditScoreRequest(ssn);
        ResponseEntity<CreditScoreResponse> creditScoreResponse = restTemplate.postForEntity("http://localhost:8080/creditscore", creditScoreRequest, CreditScoreResponse.class);
        return creditScoreResponse.getBody().getCreditScore();
    }
}
