package com.diamantetechcoaching.loanmanagement.ui;

import com.diamantetechcoaching.loanmanagement.LoanApplicationRequest;
import com.diamantetechcoaching.loanmanagement.LoanApplicationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Controller for handling the loan application form UI.
 * This controller is responsible for displaying the form and submitting the application to the backend.
 */
@Controller
@RequestMapping("/ui/loan")
public class LoanApplicationFormController {

    private final RestTemplate restTemplate;
    
    @Value("${backend.api.url:http://localhost:8080}")
    private String backendApiUrl;
    
    @Autowired
    public LoanApplicationFormController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Displays the loan application form.
     */
    @GetMapping("/apply")
    public String showLoanApplicationForm(Model model) {
        model.addAttribute("loanApplication", new LoanApplicationRequest());
        model.addAttribute("companyName", "Legacy Financial");
        return "loan-application";
    }

    /**
     * Processes the loan application form submission.
     */
    @PostMapping("/submit")
    public String submitLoanApplication(
            @ModelAttribute("loanApplication") LoanApplicationRequest loanApplication,
            BindingResult bindingResult,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("companyName", "Legacy Financial");
            return "loan-application";
        }
        
        try {
            // Call the backend API to process the loan application
            LoanApplicationResponse response = restTemplate.postForObject(
                    backendApiUrl + "/loan/apply",
                    loanApplication,
                    LoanApplicationResponse.class);
            
            // Add the response to the model
            if (response != null) {
                model.addAttribute("response", response);
                model.addAttribute("approved", "Approved".equals(response.getStatus()));
                model.addAttribute("status", response.getStatus());
            } else {
                // Handle null response
                model.addAttribute("error", "Unable to process your application at this time. Please try again later.");
                return "error";
            }
            model.addAttribute("companyName", "Legacy Financial");
        } catch (Exception e) {
            // Handle exceptions
            model.addAttribute("error", "An error occurred while processing your application: " + e.getMessage());
            model.addAttribute("companyName", "Legacy Financial");
            return "error";
        }
        
        return "loan-result";
    }
}
