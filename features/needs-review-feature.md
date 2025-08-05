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

### Scenario: Loan request is between 4x and 10x monthly income
Given the applicant requests a loan greater than 4 times but not more than 10 times their monthly income
And does not meet criteria for auto-approval or auto-rejection
When the loan application is processed
Then the application status should be set to "Needs Manual Review"