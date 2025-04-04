package com.menger.controller_tool_api;

import com.menger.controller_tool_api.models.Atendimento;
import com.menger.controller_tool_api.models.Prestadores;
import com.menger.controller_tool_api.services.DBConnection;
import com.menger.controller_tool_api.services.ExcelReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SpringBootApplication
public class ControllerToolApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ControllerToolApiApplication.class, args);

		ExcelReader excelReader = new ExcelReader();
		List<Atendimento> atendimentos = excelReader.readAtendimentos("C:\\Users\\Matheus\\Desktop\\Projeto\\DadosFull.xlsx");

		DBConnection dbConnection = new DBConnection(
				"jdbc:postgresql://localhost:5432/MovidaFornecedoresDB",
				"postgres",
				"postgres"
		);
		List<Prestadores> prestadores = dbConnection.getPrestadores();

		cruzamentoDados(atendimentos, prestadores);

		logResultado(atendimentos);
	}

	private static void cruzamentoDados(List<Atendimento> atendimentos, List<Prestadores> prestadores) {
		for (Atendimento atendimento : atendimentos) {
			for (Prestadores prestador : prestadores) {
				if (atendimento.getCodsap().equalsIgnoreCase(prestador.getCodsap())) {
					atendimento.setCnpj(prestador.getCnpj());
					atendimento.setTelefone(prestador.getTelefone());
					break;
				}
			}
		}
	}

	private static void logResultado(List<Atendimento> atendimentos) throws IOException {

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
			System.out.println("[ERROR]" + e);
		}
	}
}

