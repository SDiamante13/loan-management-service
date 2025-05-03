# User Stories

## Feature: Auto-reject loan application

### Scenario: Debt-to-income ratio is over 50%
Given the applicant has a DTI greater than 50%
When the loan application is processed
Then the application status should be set to "Rejected"

### Scenario: Applicant has a recent bankruptcy
Given the applicant filed for bankruptcy within the last 3 years
When the loan application is processed
Then the application status should be set to "Rejected"

### Scenario: Applicant missed more than 2 payments in the last 6 months
Given the applicant has missed 3 or more payments in the past 6 months
When the loan application is processed
Then the application status should be set to "Rejected"

### Scenario: Loan amount exceeds 10x monthly income and documentation is insufficient
Given the applicant requests a loan greater than 10 times their monthly income
And required supporting documentation is not provided
When the loan application is processed
Then the application status should be set to "Rejected"

## 🚀 New Feature: Flag application for manual review

### Scenario: Credit score is between 600 and 749
Given the applicant has a credit score between 600 and 749
And does not meet criteria for auto-approval or auto-rejection
When the loan application is processed
Then the application status should be set to "Needs Manual Review"

### Scenario: DTI is between 36% and 50%
Given the applicant has a debt-to-income ratio between 36% and 50%
And does not meet criteria for auto-approval or auto-rejection
When the loan application is processed
Then the application status should be set to "Needs Manual Review"

### Scenario: Applicant has missed one or two payments in the last 6 months
Given the applicant has missed 1 or 2 payments in the past 6 months
And does not meet criteria for auto-rejection
When the loan application is processed
Then the application status should be set to "Needs Manual Review"

### Scenario: Loan request is between 4x and 10x monthly income
Given the applicant requests a loan greater than 4 times but not more than 10 times their monthly income
And does not meet criteria for auto-approval or auto-rejection
When the loan application is processed
Then the application status should be set to "Needs Manual Review"


