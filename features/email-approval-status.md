# Feature: Send approval email notifications

## Scenario 1: Application is approved

Given a loan application is submitted
And the application is approved
And the applicant provided an email address
When the application is processed
Then an approval email should be sent to the applicant

```text
Email Subject:
Your loan application has been approved

Email Body:
Dear [First Name] [Last Name],

Congratulations! Your loan application has been approved.

Loan Details:
- Loan Amount: $[Requested Amount]
- Monthly Income: $[Monthly Income]
- Credit Score: [Credit Score]
- Debt-to-Income Ratio: [DTI]%

If you have any questions, feel free to contact our support team.

Best regards,  
Loan Approval Team
```

## Scenario 2: Application is rejected

Given a loan application is submitted
And the application is rejected
When the application is processed
Then no email should be sent

## Scenario 3: Applicant email is missing

Given a loan application is submitted
And the application is approved
And the applicant did not provide an email address
When the application is processed
Then no email should be sent
And a warning should be logged
And the application should still be marked as approved