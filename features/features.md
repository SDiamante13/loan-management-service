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

## Feature: Reasoning for not being auto-approved 

### Scenario: Provide Reason for not being auto-approved
Given the applicant does not meet criteria for auto-approval
When the loan application is processed
Then the reason(s) should be detailed for the user
