package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by Helder on 26/01/2018.
 */

public class Item implements EntidadePersistivel {

    private int id_local;
    private int id_web;
    private int id_produto;
    private int id_pedido;
    private int quantidade;
    private int emp_codigo;

    public Item() {

    }

    public Item(int id, int id_produto, int id_pedido, int quantidade, int emp_codigo) {
        this.id_local = id;
        this.id_produto = id_produto;
        this.id_pedido = id_pedido;
        this.quantidade = quantidade;
        this.emp_codigo = emp_codigo;
    }

    public int getEmp_codigo() {
        return emp_codigo;
    }

    public void setEmp_codigo(int emp_codigo) {
        this.emp_codigo = emp_codigo;
    }

    public int getId_web() {
        return id_web;
    }

    public void setId_web(int id_web) {
        this.id_web = id_web;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int getId() {
        return this.id_local;
    }

    @Override
    public void setId(int id) {
        this.id_local = id;
    }
}
