package com.menger.controller_tool_api.services;

import com.menger.controller_tool_api.models.Atendimento;
import com.menger.controller_tool_api.models.Prestadores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class Integration {

    @Value("${excel.file.path}")
    private String excelFilePath;

    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.username}")
    private String dbUsername;

    @Value("${database.password}")
    private String dbPassword;

    private final ExcelReader excelReader;
    private final DBConnection dbConnection;

    public Integration(ExcelReader excelReader, DBConnection dbConnection) {
        this.excelReader = excelReader;
        this.dbConnection = dbConnection;
    }

    public String runApp() throws IOException {
        List<Atendimento> atendimentos = excelReader.readAtendimentos(excelFilePath);
        List<Prestadores> prestadores = dbConnection.getPrestadores();

        cruzamentoDados(atendimentos, prestadores);
        logResultado(atendimentos);

        return exibirResultado(atendimentos);  // Agora consistente
    }

    public void cruzamentoDados(List<Atendimento> atendimentos, List<Prestadores> prestadores) {
        for(Atendimento atendimento : atendimentos) {
            for(Prestadores prestador : prestadores) {
                if (atendimento.getCodsap().equalsIgnoreCase(prestador.getCodsap())) {
                    atendimento.setCnpj(prestador.getCnpj());
                    atendimento.setTelefone(prestador.getTelefone());
                    break;
                }
            }
        }
    }

    public String exibirResultado(List<Atendimento> atendimentos) {
        StringBuilder result = new StringBuilder();
        result.append("Resultado:\n");
        result.append("-".repeat(100)).append("\n");
        result.append(String.format("%-8s %-12s %-7s %-40s %-15s %-15s%n",
                "Placa", "Atendimento", "Data", "Razão Social", "CNPJ", "Telefone"));
        result.append("-".repeat(100)).append("\n");

        for (Atendimento atendimento : atendimentos) {
            result.append(String.format("%-8s %-12s %-7s %-40s %-15s %-15s%n",
                    atendimento.getPlaca(),
                    atendimento.getTipoAtendimento(),
                    atendimento.getData(),
                    atendimento.getFornecedor(),
                    atendimento.getCnpj() != null ? atendimento.getCnpj() : "N/A",
                    atendimento.getTelefone() != null ? atendimento.getTelefone() : "N/A"));
        }

        return result.toString();
    }

    public void logResultado(List<Atendimento> atendimentos) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt"))) {
            writer.println("-".repeat(140));
            writer.printf("%-8s %-12s %-7s %-40s %-15s %-15s %-8s %-15s%n",
                    "Placa", "Atendimento", "Data", "Razão Social", "CNPJ", "Telefone", "Estado", "Cidade");
            writer.println("-".repeat(140));

            for (Atendimento atendimento : atendimentos) {
                writer.printf("%-8s %-12s %-7s %-40s %-15s %-15s %-8s %-15s%n",
                        atendimento.getPlaca(),
                        atendimento.getTipoAtendimento(),
                        atendimento.getData(),
                        atendimento.getFornecedor(),
                        atendimento.getCnpj() != null ? atendimento.getCnpj() : "N/A",
                        atendimento.getTelefone() != null ? atendimento.getTelefone() : "N/A",
                        atendimento.getEstado(),
                        atendimento.getCidade());
            }
        } catch (IOException e) {
            throw new IOException("[ERROR]"+e);
        }
    }
}
