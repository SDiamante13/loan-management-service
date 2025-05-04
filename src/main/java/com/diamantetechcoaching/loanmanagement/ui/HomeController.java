package com.diamantetechcoaching.loanmanagement.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling the home page and redirects.
 * This provides a convenient entry point to the UI.
 */
@Controller
public class HomeController {

    /**
     * Redirects the root UI path to the loan application form.
     */
    @GetMapping({"", "/"})
    public String home() {
        return "redirect:/ui/loan/apply";
    }
}
