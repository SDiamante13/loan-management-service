# Feature: Auto-approve loan application

## ✅ Scenario: Applicant meets all criteria for auto-approval
Given the applicant has a credit score of 750 or higher
And the applicant has a debt-to-income ratio of 35% or lower
And the requested loan amount is less than or equal to 4 times their monthly income
When the loan application is processed
Then the application status should be set to "Approved"

## ✅ Scenario: Applicant does not meet credit score requirement for auto-approval
Given the applicant has a credit score below 750
And meets all other auto-approval criteria
When the loan application is processed
Then the application status should not be set to "Approved"

## ✅ Scenario: Applicant exceeds the DTI threshold
Given the applicant has a debt-to-income ratio above 35%
When the loan application is processed
Then the application status should not be set to "Approved"

## ✅ Scenario: Loan amount exceeds 4x monthly income
Given the applicant requests a loan greater than 4 times their monthly income
When the loan application is processed
Then the application status should not be set to "Approved"