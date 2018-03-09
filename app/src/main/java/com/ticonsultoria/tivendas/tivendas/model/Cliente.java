package com.ticonsultoria.tivendas.tivendas.model;

import java.util.Date;

/**
 * Created by Helder on 18/01/2018.
 */

public class Cliente implements EntidadePersistivel {

    private int id_local;
    private int id_web;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String nomeMercado;
    private boolean ativo;
    private int emp_codigo;
    private Date last_sync;

    public Cliente(){

    }

    public Cliente(int id, String nome, String cpf, String nomeMercado, boolean ativo, int emp_codigo) {
        this.id_local = id;
        this.nome = nome;
        this.cpf = cpf;
        this.nomeMercado = nomeMercado;
        this.ativo = ativo;
        this.emp_codigo = emp_codigo;
    }

    public Date getLast_sync() {
        return last_sync;
    }

    public void setLast_sync(Date last_sync) {
        this.last_sync = last_sync;
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
