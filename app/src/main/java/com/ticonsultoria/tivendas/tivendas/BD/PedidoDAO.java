package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Pedido;

import java.util.Date;

/**
 * Created by mpire on 26/01/2018.
 */

public class PedidoDAO extends DAOBasico<Pedido> {

    public static final String NOME_TABELA = "pedidos";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_CODIGO_PEDIDO = "codigo_pedido";
    public static final String COLUNA_NUMERO_PEDIDO = "numero_pedido";
    public static final String COLUNA_DATA = "data";
    public static final String COLUNA_ID_VENDEDOR = "id_vendedor";
    public static final String COLUNA_ID_CLIENTE = "id_cliente";
    public static final String COLUNA_FORMA_PAGAMENTO = "forma_pagamento";
    public static final String COLUNA_PRECO_TOTAL = "preco_total";
    public static final String COLUNA_ATIVO = "ativo";

    public static final String SCRIPT_CRIACAO_TABELA_PEDIDOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_CODIGO_PEDIDO + " INTEGER,"
            + COLUNA_NUMERO_PEDIDO + " TEXT,"
            + COLUNA_DATA + " TEXT,"
            + COLUNA_ID_VENDEDOR + " INT,"
            + COLUNA_ID_CLIENTE + " INT,"
            + COLUNA_FORMA_PAGAMENTO + " TEXT,"
            + COLUNA_PRECO_TOTAL + " DOUBLE,"
            + COLUNA_ATIVO + " BOOLEAN"
            + ")";

    public static final String SCRIPT_DELECAO_TABELA_PEDIDOS =  "DROP TABLE IF EXISTS " + NOME_TABELA;


    public PedidoDAO(Context context) {
        super(context);
    }

    @Override
    public String getNomeColunaPrimaryKey() {
        return COLUNA_ID;
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public String getNomeColunaAtivo() {
        return COLUNA_ATIVO;
    }

    @Override
    public ContentValues entidadeParaContentValues(Pedido pedido) {
        ContentValues values = new ContentValues();
        if(pedido.getId() > 0) {
            values.put(COLUNA_ID, pedido.getId());
        }
        if(pedido.getCodigo_pedido() > 0) {
            values.put(COLUNA_CODIGO_PEDIDO, pedido.getCodigo_pedido());
        }
        values.put(COLUNA_NUMERO_PEDIDO, pedido.getNumero_pedido());
        values.put(COLUNA_DATA, String.valueOf(pedido.getData().getTime()));
        values.put(COLUNA_ID_VENDEDOR, pedido.getId_vendedor());
        values.put(COLUNA_ID_CLIENTE, pedido.getId_cliente());
        values.put(COLUNA_FORMA_PAGAMENTO, pedido.getFormaPagamento());
        values.put(COLUNA_PRECO_TOTAL, pedido.getPrecoTotal());
        values.put(COLUNA_ATIVO, pedido.isAtivo());

        return values;
    }

    @Override
    public Pedido contentValuesParaEntidade(ContentValues contentValues) {
        Pedido pedido = new Pedido();

        pedido.setId(contentValues.getAsInteger(COLUNA_ID));
        pedido.setAtivo(contentValues.getAsInteger(COLUNA_ATIVO) > 0);
        pedido.setPrecoTotal(contentValues.getAsDouble(COLUNA_PRECO_TOTAL));
        pedido.setFormaPagamento(contentValues.getAsString(COLUNA_FORMA_PAGAMENTO));
        pedido.setId_cliente(contentValues.getAsInteger(COLUNA_ID_CLIENTE));
        pedido.setId_vendedor(contentValues.getAsInteger(COLUNA_ID_VENDEDOR));
        pedido.setData(new Date(Long.valueOf(contentValues.getAsString(COLUNA_DATA))));
        pedido.setCodigo_pedido(contentValues.getAsInteger(COLUNA_CODIGO_PEDIDO));
        pedido.setNumero_pedido(contentValues.getAsString(COLUNA_NUMERO_PEDIDO));

        return pedido;
    }
}
