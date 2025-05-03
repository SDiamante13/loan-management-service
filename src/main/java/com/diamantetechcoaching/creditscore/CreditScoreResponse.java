package com.diamantetechcoaching.creditscore;

public record CreditScoreResponse(int creditScore) {

    public static CreditScoreResponse withCreditScore(int creditScore) {
        return new CreditScoreResponse(creditScore);
    }
}
