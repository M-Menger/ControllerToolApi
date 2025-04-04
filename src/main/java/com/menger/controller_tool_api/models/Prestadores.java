package com.menger.controller_tool_api.models;

import lombok.Data;

@Data
public class Prestadores {
    private String razaoSocial;
    private String cnpj;
    private String estado;
    private String cidade;
    private String telefone;
    private String local;
    private String codsap;
}
