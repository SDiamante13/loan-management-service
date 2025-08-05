package com.diamantetechcoaching.loanmanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AlmanacService {

    private static AlmanacService instance;
    private final RestTemplate restTemplate;

    private AlmanacService() {
        this.restTemplate = new RestTemplate();
    }

    public static synchronized AlmanacService getInstance() {
        if (instance == null) {
            instance = new AlmanacService();
        }
        return instance;
    }

    public int fetchCreditScore(String ssn) {
        CreditScoreRequest creditScoreRequest = new CreditScoreRequest(ssn);
        ResponseEntity<CreditScoreResponse> creditScoreResponse = restTemplate.postForEntity("http://localhost:8080/creditscore", creditScoreRequest, CreditScoreResponse.class);
        return creditScoreResponse.getBody().getCreditScore();
    }
}
