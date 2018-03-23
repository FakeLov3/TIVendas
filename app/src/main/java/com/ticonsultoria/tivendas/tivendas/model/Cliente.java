package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by Helder on 18/01/2018.
 */

public class Cliente implements EntidadePersistivel {

    private int id_local;
    private int id_web;
    private String nome_cliente;
    private String email;
    private String telefone;
    private String cpf;
    private String nome_mercado;
    private String ativo;
    private int emp_codigo;
    private String last_sync;

    public Cliente(){

    }

    public Cliente(int id, String nome, String cpf, String nomeMercado, String ativo, int emp_codigo) {
        this.id_local = id;
        this.nome_cliente = nome;
        this.cpf = cpf;
        this.nome_mercado = nomeMercado;
        this.ativo = ativo;
        this.emp_codigo = emp_codigo;
    }

    public String getLast_sync() {
        return last_sync;
    }

    public void setLast_sync(String last_sync) {
        this.last_sync = last_sync;
    }

    public String isAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeMercado() {
        return nome_mercado;
    }

    public void setNomeMercado(String nomeMercado) {
        this.nome_mercado = nomeMercado;
    }

    public int getId_web() {
        return id_web;
    }

    public void setId_web(int id_web) {
        this.id_web = id_web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int getId() {
        return this.id_local;
    }

    @Override
    public void setId(int id) { this.id_local = id;

    }

    public int getEmp_codigo() {
        return emp_codigo;
    }

    public void setEmp_codigo(int emp_codigo) {
        this.emp_codigo = emp_codigo;
    }
}
