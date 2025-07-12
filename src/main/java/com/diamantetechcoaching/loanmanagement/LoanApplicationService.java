package com.diamantetechcoaching.loanmanagement;

// Import statements for the loan management service
import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;
import com.diamantetechcoaching.loanmanagement.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // This is for making this class a service

import java.math.BigDecimal; // For handling big decimal numbers
import java.time.LocalDateTime; // For timestamp handling

/**
 * This is the LoanApplicationService class
 * It processes loan applications
 * Author: Developer
 * Date: Today
 * Version: 1.0
 */
@Service // This annotation makes this class a Spring service bean
public class LoanApplicationService {

    // Repository for loan applications
    @Autowired // This injects the repository dependency
    private LoanApplicationRepository loanApplicationRepository;
    
    // Dead code - unused method
    // private String oldMethod() {
    //     return "This method is not used anymore";
    // }
    
    // Unused variable
    private static final String UNUSED_CONSTANT = "This is never used";

    /**
     * This method processes loan applications
     * It takes a request and returns a response
     * @param request the loan application request
     * @return the loan application response
     */
    public LoanApplicationResponse processLoanApplication(LoanApplicationRequest request) {
        // Get the SSN from the request
        String ssn = request.getSsn(); // Social Security Number
        
        // Fetch credit score using the SSN
        int credit = AlmanacService.fetchCreditScore(ssn); // This calls an external service
        
        // Get first name from request
        String firstName = request.getFirstName(); // Customer's first name
        
        // Get last name from request  
        String lastName = request.getLastName(); // Customer's last name
        
        // Get monthly income
        double income = request.getMonthlyIncome(); // How much they make per month
        
        // Get monthly debt
        double debt = request.getMonthlyDebt(); // How much they owe per month
        
        // Get requested loan amount
        double loanAmount = request.getRequestedAmount(); // Amount they want to borrow
        
        // Calculate debt to income ratio
        double dti = (debt / income) * 100; // DTI calculation - very important!
        
        // Check if credit score is good enough
        boolean creditOk = credit >= 750; // 750 is our minimum credit score
        
        // Check if DTI is acceptable
        boolean dtiOk = dti <= 35; // 35% is our maximum DTI
        
        // Check if loan amount is reasonable
        boolean amountOk = loanAmount <= income * 4; // Can't be more than 4x annual income
        
        // Set default status to rejected
        String status = "Rejected"; // Default is rejected
        
        // If all conditions are met, approve the loan
        if (creditOk && dtiOk && amountOk) { // All three conditions must be true
            status = "Approved"; // Change status to approved
        }
        
        // Create the response object
        LoanApplicationResponse response = new LoanApplicationResponse( // Constructor call
                status, // The approval status
                credit, // Credit score
                income, // Monthly income
                debt, // Monthly debt
                loanAmount, // Requested amount
                dti // Debt to income ratio
        );
        
        // Create entity for database persistence
        LoanEntity entity = new LoanEntity(); // New entity instance
        
        // Set all the entity fields
        entity.setFirstName(firstName); // Set first name
        entity.setLastName(lastName); // Set last name
        entity.setCreditScore(credit); // Set credit score
        entity.setMonthlyIncome(BigDecimal.valueOf(income)); // Convert to BigDecimal
        entity.setMonthlyDebt(BigDecimal.valueOf(debt)); // Convert to BigDecimal
        entity.setRequestedAmount(BigDecimal.valueOf(loanAmount)); // Convert to BigDecimal
        entity.setDebtToIncomeRatio(BigDecimal.valueOf(dti)); // Convert to BigDecimal
        entity.setApplicationStatus(status); // Set the status
        entity.setSubmissionTimestamp(LocalDateTime.now()); // Set current timestamp
        
        // Try to save to database
        try {
            loanApplicationRepository.save(entity); // Save the entity
        } catch (Exception e) { // Catch any exceptions
            // Print error message to console
            System.err.println("Failed to persist loan application: " + e.getMessage());
            // Throw runtime exception
            throw new RuntimeException("Database error", e); // Re-throw as runtime exception
        }
        
        // Return the response
        return response; // Send back the response object
    }
}
