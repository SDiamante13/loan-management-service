package com.diamantetechcoaching.loanmanagement.domain;

public enum LoanStatus {
    APPROVED("Approved"),
    REJECTED("Rejected"),
    NEEDS_MANUAL_REVIEW("Needs Manual Review");

    private final String status;

    LoanStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
