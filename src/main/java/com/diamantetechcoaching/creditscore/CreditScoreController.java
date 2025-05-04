package com.diamantetechcoaching.creditscore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class CreditScoreController {

    @PostMapping("/creditscore")
    public ResponseEntity<CreditScoreResponse> creditScore(@RequestBody CreditScoreRequest creditScoreRequest) {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        if (randomNumber == 0) {
            return ResponseEntity.internalServerError().build();
        }
        if (creditScoreRequest.getSsn().equals("123")) {
            return ResponseEntity.ok(CreditScoreResponse.withCreditScore(750));
        } else if (creditScoreRequest.getSsn().equals("999")) {
            return ResponseEntity.ok(CreditScoreResponse.withCreditScore(700));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
