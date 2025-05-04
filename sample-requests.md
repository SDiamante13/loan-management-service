# Requests for Loan Approval

## Approved Loan
```bash
curl -X POST http://localhost:8080/loan/apply \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jim",
    "lastName": "Rohn",
    "ssn": "123",
    "monthlyIncome": 5000,
    "monthlyDebt": 1500,
    "requestedAmount": 18000
  }'
```

## Rejected Loan

```bash
curl -X POST http://localhost:8080/loan/apply \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Scobby",
    "lastName": "Doo",
    "ssn": "999",
    "monthlyIncome": 5000,
    "monthlyDebt": 1500,
    "requestedAmount": 18000
  }'
```