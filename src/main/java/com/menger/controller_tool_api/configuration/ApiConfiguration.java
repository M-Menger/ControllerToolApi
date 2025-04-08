package com.menger.controller_tool_api.configuration;

import com.menger.controller_tool_api.services.DBConnection;
import com.menger.controller_tool_api.services.ExcelReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.username}")
    private String dbUsername;

    @Value("${database.PASSWORD}")
    private String dbPassword;

    @Bean
    public ExcelReader excelReader() {
        return new ExcelReader();
    }

    @Bean
    public DBConnection getConnection() {
        return new DBConnection();
    }
}
