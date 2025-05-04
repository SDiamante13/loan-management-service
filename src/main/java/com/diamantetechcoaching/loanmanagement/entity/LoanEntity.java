package com.diamantetechcoaching.loanmanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "credit_score")
    private Integer creditScore;
    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;
    @Column(name = "monthly_debt")
    private BigDecimal monthlyDebt;
    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;
    @Column(name = "debt_income_ratio")
    private BigDecimal debtToIncomeRatio;
    @Column(name = "status")
    private String applicationStatus;
    @Column(name = "created_at")
    private LocalDateTime submissionTimestamp;

    public LoanEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyDebt() {
        return monthlyDebt;
    }

    public void setMonthlyDebt(BigDecimal monthlyDebt) {
        this.monthlyDebt = monthlyDebt;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getDebtToIncomeRatio() {
        return debtToIncomeRatio;
    }

    public void setDebtToIncomeRatio(BigDecimal debtToIncomeRatio) {
        this.debtToIncomeRatio = debtToIncomeRatio;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getSubmissionTimestamp() {
        return submissionTimestamp;
    }

    public void setSubmissionTimestamp(LocalDateTime submissionTimestamp) {
        this.submissionTimestamp = submissionTimestamp;
    }
}
