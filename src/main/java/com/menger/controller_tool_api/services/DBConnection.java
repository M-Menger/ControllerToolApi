package com.menger.controller_tool_api.services;

import com.menger.controller_tool_api.models.Prestadores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBConnection {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pass;

    public Connection getConnection() throws SQLException {
        try {
            DriverManager.setLoginTimeout(10);
            return DriverManager.getConnection(dbUrl, user, pass);
        } catch (SQLException e) {
            System.err.println("Erro detalhado:");
            System.err.println("URL: " + dbUrl);
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            throw e;
        }
    }

    public List<Prestadores> getPrestadores() {
        List<Prestadores> prestadores = new ArrayList<>();
        String sql = "SELECT \"cnpj_cpf\", \"cod_cliente\", \"uf\", \"cidade\", \"telefone\", \"latlong\" FROM fornecedores";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestadores prestador = new Prestadores();
                prestador.setCodsap(rs.getString("cod_cliente"));
                prestador.setCnpj(rs.getString("cnpj_cpf"));
                prestador.setEstado(rs.getString("uf"));
                prestador.setCidade(rs.getString("cidade"));
                prestador.setTelefone(rs.getString("telefone"));
                prestador.setLocal(rs.getString("latlong"));

                prestadores.add(prestador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestadores;
    }
}