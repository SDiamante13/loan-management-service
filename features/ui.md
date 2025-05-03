Feature: Loan application submission form
Scenario 1: User submits a loan application through the UI
Given the user is on the loan application page
When the user fills out and submits the form
Then the form data should be sent as a POST request to /loan/apply
And the backend should process the application and return the approval status

Form Fields (all fields required):

- First Name 
- Last Name 
- Email Address 
- Credit Score Number 
- Monthly Income (USD)   
- Monthly Debt (USD)    
- Requested Loan Amount

Submission behavior:

The form should POST to http://localhost:8080/loan/apply

The request should use application/json

Example JSON payload:

```json
{
  "firstName": "Alex",
  "lastName": "Johnson",
  "email": "alex.johnson@example.com",
  "creditScore": 780,
  "monthlyIncome": 6000,
  "monthlyDebt": 1800,
  "requestedAmount": 20000
}
```

Response:

The backend returns a JSON object with the loan status:

```json
{
  "status": "Rejected",
  "creditScore": 700,
  "monthlyIncome": 5000.0,
  "monthlyDebt": 1500.0,
  "requestedAmount": 18000.0,
  "dti": 30.0
}
```
