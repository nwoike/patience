package com.patience.klondike;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.patience.servlet.filter.SimpleCORSFilter;

@Configuration
@PropertySource(value = "classpath:sql.properties")
public class AppConfig {

    @Bean
    SimpleCORSFilter corsFilter() {
        return new SimpleCORSFilter();
    }
}