package com.chwonghm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for ShopifyDemo
 *
 * @author Charles Wong
 */
@SpringBootApplication
public class ShopifyDemoApplication {

    /**
     * Main method for ShopifyDemo. Runs this Spring application with provided arguments.
     *
     * @param args an array of String command line arguments passed when running this application
     */
    public static void main(String[] args) {
        SpringApplication.run(ShopifyDemoApplication.class, args);
    }

}