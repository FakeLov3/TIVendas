package com.ticonsultoria.tivendas.tivendas.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Date;


/**
 * Created by Helder on 23/01/2018.
 */

public class Produto implements EntidadePersistivel {

    private int id_local;
    private int id_web;
    private String nome_produto;
    private double preco;
    private String categoria;
    private String fornecedor;
    private String marca;
    private int quantidade;
    private boolean ativo;
    private byte [] foto;
    private int emp_codigo;
    private Date dataCriacao;
    private String last_sync;

    public Produto() {
    }

    public Produto(int id, String nome_produto, double preco, String categoria, String fornecedor, String marca, Date dataCriacao) {
        this.id_local = id;
        this.nome_produto = nome_produto;
        this.preco = preco;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.marca = marca;
        this.dataCriacao = dataCriacao;
    }

    public String getLast_sync() {
        return last_sync;
    }

    public void setLast_sync(String last_sync) {
        this.last_sync = last_sync;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    public int getId() {
        return id_local;
    }

    public void setId(int id) {
        this.id_local = id;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getId_web() {
        return id_web;
    }

    public void setId_web(int id_web) {
        this.id_web = id_web;
    }

    public void setFotoImageView(ImageView img){
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,saida);
        foto  = saida.toByteArray();

    }

    public Bitmap getImageView(){

            Bitmap raw  = BitmapFactory.decodeByteArray(foto,0,foto.length);
            return raw;

    }

//   TODO: fazer um código para transformar foto em array de bytes e outro para fazer o contrário
}
