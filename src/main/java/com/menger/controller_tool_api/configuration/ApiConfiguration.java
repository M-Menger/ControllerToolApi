package com.menger.controller_tool_api.configuration;

import com.menger.controller_tool_api.services.ExcelReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

    @Bean
    public ExcelReader excelReader() {
        return new ExcelReader();
    }

}
