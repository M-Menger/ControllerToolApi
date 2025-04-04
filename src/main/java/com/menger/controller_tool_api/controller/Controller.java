package com.menger.controller_tool_api.controller;

import com.menger.controller_tool_api.services.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/atendimentos")
public class Controller {

    @Autowired
    private Integration integration;

    @GetMapping()
    public String atendimentos() throws IOException {
        return integration.runApp();
    }

}
