package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by mpire on 18/01/2018.
 */

public class Usuario {

    private String login;
    private String senha;
    private boolean adm;

    public Usuario() {
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
