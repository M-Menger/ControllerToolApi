package com.menger.controller_tool_api.models;

import lombok.Data;

@Data
public class Atendimento {
    public String placa;
    public String tipoAtendimento;
    public String os;
    public String data;
    public String status;
    public String dataAbertura;
    public String fornecedor;
    public String estado;
    public String cidade;
    public String codsap;

    public String cnpj;
    public String telefone;
    public String localizacao;
}
