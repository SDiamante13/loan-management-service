package com.diamantetechcoaching.creditscore;

public class CreditScoreRequest {

    private String ssn;

    public CreditScoreRequest(String ssn) {
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
