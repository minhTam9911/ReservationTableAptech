package com.bookingtable.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.paypal.base.rest.APIContext;

@Configuration
@PropertySource("classpath:application.properties")
public class PaypalConfig {

	

	    @Autowired
	    private Environment environment;

	    public PaypalConfig() { // Remove unnecessary constructor argument
	    }

	    public String getClientId() {
	        return environment.getProperty("paypal.client-id");
	    }

	    public String getClientSecret() {
	        return environment.getProperty("paypal.client-secret");
	    }

	    public String getMode() {
	        return environment.getProperty("paypal.mode");
	    }

	    @Bean
	    public APIContext apiContext() {
	        return new APIContext(getClientId(), getClientSecret(), getMode()); // Use getters for properties
	    }
	
	
}
