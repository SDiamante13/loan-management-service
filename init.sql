CREATE TABLE IF NOT EXISTS loan_applications (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    credit_score INT NOT NULL,
    monthly_income NUMERIC(12, 2) NOT NULL,
    monthly_debt NUMERIC(12, 2) NOT NULL,
    requested_amount NUMERIC(12, 2) NOT NULL,
    debt_income_ratio NUMERIC(12, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
