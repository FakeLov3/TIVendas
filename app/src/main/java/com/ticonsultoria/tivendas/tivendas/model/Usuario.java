package com.ticonsultoria.tivendas.tivendas.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by mpire on 18/01/2018.
 */

public class Usuario implements EntidadePersistivel {

    private int id_local;
    private int id_web;
    private String nome;
    private String email;
    private String telefone;
    private byte [] imagem_usuario_bytes;
    private String imagem_usuario;
    private String login;
    private String senha;
    private boolean adm;
    private boolean cadastrarProdutos;
    private boolean ativo;
    private int emp_codigo;

    public Usuario() {
    }

    public Usuario(int id_local,int id_web, String login, String senha, boolean adm, boolean cadastrarProdutos, boolean ativo, String nome, String email, String telefone, byte[] imagem) {
        this.id_local = id_local;
        this.login = login;
        this.senha = senha;
        this.adm = adm;
        this.cadastrarProdutos = cadastrarProdutos;
        this.ativo = ativo;
        this.id_web = id_web;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.imagem_usuario_bytes = imagem;
    }

    public String getImagem_usuario() {
        return imagem_usuario;
    }

    public void setImagem_usuario(String imagem_usuario) {
        this.imagem_usuario = imagem_usuario;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public int getEmp_codigo() {
        return emp_codigo;
    }

    public void setEmp_codigo(int emp_codigo) {
        this.emp_codigo = emp_codigo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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
            return "Vendedor";
        }
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }


    public int getId_web() {
        return id_web;
    }

    public void setId_web(int id_web) {
        this.id_web = id_web;
    }

    @Override
    public int getId() {
        return id_local;
    }

    @Override
    public void setId(int id) {
        this.id_local = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public byte[] getImagem_usuario_bytes() {
        return imagem_usuario_bytes;
    }

    public void setImagem_usuario_bytes(byte[] imagem_usuario_bytes) {
        this.imagem_usuario_bytes = imagem_usuario_bytes;
    }

    public void setFotoImageView(ImageView img){
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,saida);
        imagem_usuario_bytes = saida.toByteArray();
    }

    public Bitmap getImageView(){
        Bitmap raw  = BitmapFactory.decodeByteArray(imagem_usuario_bytes,0, imagem_usuario_bytes.length);
        return raw;

    }
}

