package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by Helder on 18/01/2018.
 */

public class Cliente {

    private int id_cliente;
    private String nome;
    private String cpf;
    private String nomeMercado;
    private boolean ativo;

    public Cliente(int id_cliente, String nome, String cpf, String nomeMercado, boolean ativo) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.cpf = cpf;
        this.nomeMercado = nomeMercado;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeMercado() {
        return nomeMercado;
    }

    public void setNomeMercado(String nomeMercado) {
        this.nomeMercado = nomeMercado;
    }
}
