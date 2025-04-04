package com.menger.controller_tool_api.models;

import lombok.Data;

@Data
public class Atendimento {
    private String placa;
    private String tipoAtendimento;
    private String os;
    private String data;
    private String status;
    private String dataAbertura;
    private String fornecedor;
    private String estado;
    private String cidade;
    private String codsap;

    private String cnpj;
    private String telefone;
    private String localizacao;
}
