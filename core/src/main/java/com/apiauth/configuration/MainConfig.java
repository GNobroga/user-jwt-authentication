package com.apiauth.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
