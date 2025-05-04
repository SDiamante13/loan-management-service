# Plan for Persisting Loan Applications

- [x] Scenario 1: Persist loan application to database after processing in dirty/procedural style (LoanApplicationService)
- [x] Scenario 2: Handle DB exception, log error, return 500, and do not mark application as complete (LoanApplicationService)
- [ ] Ensure docker-compose.yml provides a local PostgreSQL instance
