package com.menger.controller_tool_api.services;

import com.menger.controller_tool_api.models.Prestadores;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<Prestadores> getPrestadores() {
        List<Prestadores> prestadores = new ArrayList<>();
        String sql = "SELECT \"nome\", \"cnpj_cpf\", \"cod_cliente\", \"uf\", \"cidade\", \"telefone\", \"latlong\" FROM fornecedores";

        try (Connection conn = DriverManager.getConnection(url, user, password);
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
