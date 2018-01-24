package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by mpire on 18/01/2018.
 */

public class Usuario implements EntidadePersistivel {

    private int id;
    private String login;
    private String senha;
    private boolean adm;
    private boolean cadastrarProdutos;
    private boolean ativo;

    public Usuario() {
    }

    public Usuario(int id, String login, String senha, boolean adm, boolean cadastrarProdutos, boolean ativo) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.adm = adm;
        this.cadastrarProdutos = cadastrarProdutos;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCadastrarProdutos() {
        return cadastrarProdutos;
    }

    public void setCadastrarProdutos(boolean cadastrarProdutos) {
        this.cadastrarProdutos = cadastrarProdutos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdm() {
        return adm;
    }

    public String getStringAdm() {
        if (adm) {
            return "Administrador";
        } else {
            return "Usuário";
        }
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }
}
