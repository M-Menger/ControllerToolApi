package com.menger.controller_tool_api.controller;

import com.menger.controller_tool_api.services.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/services")
public class Controller {

    @Autowired
    private Integration integration;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file")MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Por favor, envie um arquivo!");
            }
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.badRequest().body("Apenas arquivos .xlsx são suportados");
            }

            String jsonResult = integration.processExcelFile(file);
            return ResponseEntity.ok(jsonResult);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Falha ao processar o arquivo: "+ e.getMessage());
        }
    }
}
