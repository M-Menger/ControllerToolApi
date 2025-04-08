package com.menger.controller_tool_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.menger.controller_tool_api.models.Atendimento;
import com.menger.controller_tool_api.models.Prestadores;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class Integration {

    private final ExcelReader excelReader;
    private final DBConnection dbConnection;

    public Integration(ExcelReader excelReader, DBConnection dbConnection) {
        this.excelReader = excelReader;
        this.dbConnection = dbConnection;
    }

    public String processExcelFile(MultipartFile file) throws IOException {
        List<Atendimento> atendimentos = excelReader.readAtendimentos(file.getInputStream());
        List<Prestadores> prestadores = dbConnection.getPrestadores();

        cruzamentoDados(atendimentos, prestadores);
        logResultado(atendimentos);

        return cardJson(atendimentos);
    }

    public void cruzamentoDados(List<Atendimento> atendimentos, List<Prestadores> prestadores) {
        for(Atendimento atendimento : atendimentos) {
            for(Prestadores prestador : prestadores) {
                if (atendimento.getCodsap().equalsIgnoreCase(prestador.getCodsap())) {
                    atendimento.setCnpj(prestador.getCnpj());
                    atendimento.setTelefone(prestador.getTelefone());
                    atendimento.setLocalizacao(prestador.getLocal());
                    break;
                }
            }
        }
    }

    public String cardJson(List<Atendimento> atendimentos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, String>> resultados = new ArrayList<>();

            for (Atendimento atendimento : atendimentos) {
                Map<String, String> item = new LinkedHashMap<>();
                item.put("placa", atendimento.getPlaca());
                item.put("atendimento", atendimento.getTipoAtendimento());
                item.put("status", atendimento.getStatus());
                item.put("data", atendimento.getData());
                item.put("razao_social", atendimento.getFornecedor());
                item.put("cnpj", atendimento.getCnpj() != null ? atendimento.getCnpj() : "N/A");
                item.put("telefone", atendimento.getTelefone() != null ? atendimento.getTelefone() : "N/A");
                item.put("local", atendimento.getLocalizacao() != null ? atendimento.getLocalizacao() : "N/A");
                resultados.add(item);
            }

            return mapper.writeValueAsString(resultados);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to convert to JSON\"}";
        }
    }

    public void logResultado(List<Atendimento> atendimentos) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt"))) {
            writer.println("-".repeat(180));
            writer.printf("%-8s %-12s %-7s %-40s %-15s %-15s %-8s %-15s %25s%n",
                    "Placa", "Atendimento", "Data", "Razão Social", "CNPJ", "Telefone", "Estado", "Cidade", "Localização");
            writer.println("-".repeat(180));

            for (Atendimento atendimento : atendimentos) {
                writer.printf("%-8s %-12s %-7s %-40s %-15s %-15s %-8s %-15s %25s%n",
                        atendimento.getPlaca(),
                        atendimento.getTipoAtendimento(),
                        atendimento.getData(),
                        atendimento.getFornecedor(),
                        atendimento.getCnpj() != null ? atendimento.getCnpj() : "N/A",
                        atendimento.getTelefone() != null ? atendimento.getTelefone() : "N/A",
                        atendimento.getEstado(),
                        atendimento.getCidade(),
                        atendimento.getLocalizacao());

            }
        } catch (IOException e) {
            throw new IOException("[ERROR]"+e);
        }
    }
}
