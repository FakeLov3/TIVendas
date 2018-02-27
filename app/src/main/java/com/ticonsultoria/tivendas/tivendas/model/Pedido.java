package com.ticonsultoria.tivendas.tivendas.model;

import java.util.Date;

/**
 * Created by mpire on 26/01/2018.
 */

public class Pedido implements EntidadePersistivel {

    public static final String PAGAMENTO_VISTA = "vista";
    public static final String PAGAMENTO_PRAZO = "prazo";

    private int id_local;
    private int id_web;
    private int codigo_pedido;
    private String numero_pedido;
    private Date data;
    private int id_vendedor;
    private int id_cliente;
    private String formaPagamento;
    private double precoTotal;
    private boolean ativo;
    private int emp_codigo;
    private int desconto;
    private boolean enviado;


    public Pedido() {
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public int getEmp_codigo() {
        return emp_codigo;
    }

    public void setEmp_codigo(int emp_codigo) {
        this.emp_codigo = emp_codigo;
    }

    @Override
    public int getId() {
        return id_local;
    }

    @Override
    public void setId(int id) {
        this.id_local = id;
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

    public static String getPagamentoVista() {
        return PAGAMENTO_VISTA;
    }

    public static String getPagamentoPrazo() {
        return PAGAMENTO_PRAZO;
    }

    public int getId_web() {
        return id_web;
    }

    public void setId_web(int id_web) {
        this.id_web = id_web;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
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
