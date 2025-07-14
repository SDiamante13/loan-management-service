package com.diamantetechcoaching.loanmanagement;

public enum LoanStatus {
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String status;

    LoanStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
