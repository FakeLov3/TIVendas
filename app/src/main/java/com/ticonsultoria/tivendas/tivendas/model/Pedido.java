package com.ticonsultoria.tivendas.tivendas.model;

import java.util.Date;

/**
 * Created by mpire on 26/01/2018.
 */

public class Pedido implements EntidadePersistivel {

    public static final String PAGAMENTO_VISTA = "vista";
    public static final String PAGAMENTO_PRAZO = "prazo";

    private int id;
    private int codigo_pedido;
    private String numero_pedido;
    private Date data;
    private int id_vendedor;
    private int id_cliente;
    private String formaPagamento;
    private double precoTotal;
    private boolean ativo;

    public Pedido() {

    }

    public Pedido(int id, Date data, int id_vendedor, int id_cliente, String formaPagamento, double precoTotal) {
        this.id = id;
        this.data = data;
        this.id_vendedor = id_vendedor;
        this.id_cliente = id_cliente;
        this.formaPagamento = formaPagamento;
        this.precoTotal = precoTotal;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo_pedido() {
        return codigo_pedido;
    }

    public void setCodigo_pedido(int codigo_pedido) {
        this.codigo_pedido = codigo_pedido;
    }

    public String getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(String numero_pedido) {
        this.numero_pedido = numero_pedido;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getStringFormaPagamento() {
        if (formaPagamento.equals(PAGAMENTO_VISTA)) {
            return "À vista";
        } else if (formaPagamento.equals(PAGAMENTO_PRAZO)) {
            return  "À prazo";
        } else {
            return "Inválida";
        }
    }
}
