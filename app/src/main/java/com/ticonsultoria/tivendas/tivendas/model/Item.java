package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by Helder on 26/01/2018.
 */

public class Item implements EntidadePersistivel {

    private int id;
    private int id_produto;
    private int id_pedido;
    private int quantidade;

    public Item(int id, int id_produto, int id_pedido, int quantidade) {
        this.id = id;
        this.id_produto = id_produto;
        this.id_pedido = id_pedido;
        this.quantidade = quantidade;
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
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
