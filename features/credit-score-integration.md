# Feature: Fetch Credit Score from External API

## Description

Instead of requiring the applicant to submit their credit score manually, the system should fetch the score from a credit bureau API using the applicant’s SSN during loan processing.

## ✅ Scenario 1: Applicant qualifies for auto-approval via fetched score
Given the applicant provides a valid SSN
And the credit bureau API returns a score of 750 or higher
And the applicant has a debt-to-income ratio of 35% or lower
And the requested loan amount is less than or equal to 4 times their monthly income
When the loan application is processed
Then the application status should be set to "Approved"

## ✅ Scenario 2: Applicant is not auto-approved due to low score from API
Given the applicant provides a valid SSN
And the credit bureau API returns a score below 750
When the loan application is processed
Then the application status should be set to "Rejected"

## ✅ Scenario 3: API call fails
Given the applicant provides an SSN
And the credit bureau API fails to respond or returns an error
When the loan application is processed
Then an error response is returned