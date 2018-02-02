package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by Helder on 18/01/2018.
 */

public class Cliente implements EntidadePersistivel {

    private int id_local;
    private int id_web;
    private String nome;
    private String email;
    private int telefone;

    private String cpf;
    private String nomeMercado;
    private boolean ativo;

    public Cliente(){

    }

    public Cliente(int id, String nome, String cpf, String nomeMercado, boolean ativo) {
        this.id_local = id;
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

    @Override
    public int getId() {
        return this.id_local;
    }

    @Override
    public void setId(int id) { this.id_local = id;

    }
}
