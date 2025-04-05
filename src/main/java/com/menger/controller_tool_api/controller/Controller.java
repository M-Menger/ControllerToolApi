package com.menger.controller_tool_api.controller;

import com.menger.controller_tool_api.services.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/services")
public class Controller {

    @Autowired
    private Integration integration;

    @GetMapping()
    public String atendimentos() throws IOException {
        return integration.runApp();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file")MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Por favor, envie um arquivo!");
            }
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.badRequest().body("Apenas arquivos .xlsx são suportados");
            }

            InputStream inputStream = file.getInputStream();

            return ResponseEntity.ok("Arquivo recebido com sucesso: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Falha ao processar o arquivo: "+ e.getMessage());
        }
    }

}
