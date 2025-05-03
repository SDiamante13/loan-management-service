# Feature: Store loan application in datastore

## Scenario 1: Application is successfully processed

Given a loan application is submitted
When the application is processed
Then the application should be saved to the datastore
And the saved record should include:

- Applicant's first and last name
- Email address (if provided)
- Credit score
- Monthly income
- Monthly debt
- Requested loan amount
- Calculated debt-to-income ratio
- Application status (Approved or Rejected)
- Timestamp of submission

## Scenario 2: Validation fails before processing

Given a loan application is submitted with missing or invalid fields
When the system detects invalid input before processing
Then the application should not be saved
And an error response should be returned to the caller

## Scenario 3: Database error occurs during save

Given the loan application is valid
And a database exception occurs while saving
When the application is processed
Then the system should log the error
And return a 500 error to the caller
And the application should not be considered complete
