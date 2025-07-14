package com.diamantetechcoaching.loanmanagement.domain;

public record CreditScore(int value) {

    public CreditScore {
        if (value < 300 || value > 850) {
            throw new IllegalArgumentException("Credit Score must be between 300 and 850");
        }
    }

    public boolean isApproved() {
        return value >= 750;
    }
}
