package com.diamantetechcoaching.loanmanagement.ui;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for the UI module.
 * This class provides configuration for the UI components.
 */
@Configuration
public class UiConfiguration {

    /**
     * Creates a RestTemplate bean for making HTTP requests to the backend API.
     * 
     * @param builder The RestTemplateBuilder to use
     * @return A configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
