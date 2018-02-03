package com.ticonsultoria.tivendas.tivendas.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
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
    private byte [] imagem_usuario;
    private String login;
    private String senha;
    private boolean adm;
    private boolean cadastrarProdutos;
    private boolean ativo;

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
        this.imagem_usuario = imagem;
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

    public byte[] getImagem_usuario() {
        return imagem_usuario;
    }

    public void setImagem_usuario(byte[] imagem_usuario) {
        this.imagem_usuario = imagem_usuario;
    }

    public void setFotoImageView(ImageView img){
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        int nh = (int) (bitmap.getHeight()* (512.0 / bitmap.getWidth()));
        Bitmap bitmapaux = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        Log.d("bitmapaux", ""+bitmapaux.getByteCount());
        bitmapaux.compress(Bitmap.CompressFormat.PNG,100,saida);
        imagem_usuario = saida.toByteArray();
    }

    public Bitmap getImageView(){

        Bitmap raw  = BitmapFactory.decodeByteArray(imagem_usuario,0,imagem_usuario.length);
        return raw;

    }
}

