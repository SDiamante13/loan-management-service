package com.diamantetechcoaching.loanmanagement.domain;

record CreditScore(int value) {

    CreditScore {
        if (value < 300 || value > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
    }

    boolean isApproved() {
        return value >= 750;
    }

    public boolean needsReview() {
        return value >= 600 && value < 750;
    }
}
