package com.menger.controller_tool_api.services;

import com.menger.controller_tool_api.models.Prestadores;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {


    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://db.ztzopxbmpimsduzngpjn.supabase.co:5432/postgres?user=postgres&password=Movida@2025";

        try {
            // Registrar o driver (opcional a partir do JDBC 4.0)
            Class.forName("org.postgresql.Driver");

            // Estabelecer a conexão
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conexão com o Supabase estabelecida com sucesso!");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
    }

    public List<Prestadores> getPrestadores() {
        List<Prestadores> prestadores = new ArrayList<>();
        String sql = "SELECT \"nome\", \"cnpj_cpf\", \"cod_cliente\", \"uf\", \"cidade\", \"telefone\", \"latlong\" FROM fornecedores";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestadores prestador = new Prestadores();
                prestador.setRazaoSocial(rs.getString("nome"));
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
