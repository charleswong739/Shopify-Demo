package com.chwonghm.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class configures the security setup for ShopifyDemo. Nothing is protected.
 *
 * @author Charles Wong
 */
@Configuration
public class ShopifyDemoSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configure all requests to be ignored by security.
     *
     * @param web a WebSecurity object, provided by Spring
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().anyRequest();
    }
}
