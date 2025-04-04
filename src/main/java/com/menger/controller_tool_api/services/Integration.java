package com.menger.controller_tool_api.services;

import com.menger.controller_tool_api.models.Atendimento;
import com.menger.controller_tool_api.models.Prestadores;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Integration {

    public static void cruzamentoDados(List<Atendimento> atendimentos, List<Prestadores> prestadores) {
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

    public static void exibirResultado(List<Atendimento> atendimentos) {
        System.out.println("Resultado: ");
        System.out.println("-".repeat(100));
        System.out.printf("%-8s %-12s %-7s %-40s %-15s %-15s%n",
                "Placa", "Atendimento", "Data", "Razão Social", "CNPJ", "Telefone");
        System.out.println("-".repeat(100));

        for (Atendimento atendimento : atendimentos) {
            System.out.printf("%-8s %-12s %-7s %-40s %-15s %-15s%n",
                    atendimento.getPlaca(),
                    atendimento.getTipoAtendimento(),
                    atendimento.getData(),
                    atendimento.getFornecedor(),
                    atendimento.getCnpj() != null ? atendimento.getCnpj() : "N/A",
                    atendimento.getTelefone() != null ? atendimento.getTelefone() : "N/A");
        }
    }

    public static void logResultado(List<Atendimento> atendimentos) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt"))) {
            writer.println("Resultado: ");
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
            System.out.println("[ERROR]"+e);
        }
    }
}
