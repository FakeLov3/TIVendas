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
    public static final String COLUNA_ID_LOCAL = "id_local";
    public static final String COLUNA_ID_WEB = "id_web";
    public static final String COLUNA_CODIGO_PEDIDO = "codigo_pedido";
    public static final String COLUNA_NUMERO_PEDIDO = "numero_pedido";
    public static final String COLUNA_DATA = "data";
    public static final String COLUNA_ID_VENDEDOR = "id_vendedor";
    public static final String COLUNA_ID_CLIENTE = "id_cliente";
    public static final String COLUNA_FORMA_PAGAMENTO = "forma_pagamento";
    public static final String COLUNA_PRECO_TOTAL = "preco_total";
    public static final String COLUNA_ATIVO = "ativo";
    public static final String COLUNA_EMP_CODIGO = "emp_codigo";
    public static final String COLUNA_ENVIADO = "enviado";
    public static final String COLUNA_DESCONTO = "desconto";

    public static final String SCRIPT_CRIACAO_TABELA_PEDIDOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID_LOCAL + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_CODIGO_PEDIDO + " INTEGER,"
            + COLUNA_ID_WEB + " INTEGER,"
            + COLUNA_NUMERO_PEDIDO + " TEXT,"
            + COLUNA_DATA + " TEXT,"
            + COLUNA_ID_VENDEDOR + " INT,"
            + COLUNA_ID_CLIENTE + " INT,"
            + COLUNA_FORMA_PAGAMENTO + " TEXT,"
            + COLUNA_PRECO_TOTAL + " DOUBLE,"
            + COLUNA_ATIVO + " BOOLEAN,"
            + COLUNA_EMP_CODIGO + " INTEGER,"
            + COLUNA_ENVIADO + " BOOLEAN,"
            + COLUNA_DESCONTO + " INTEGER"
            + ")";

    public static final String SCRIPT_DELECAO_TABELA_PEDIDOS =  "DROP TABLE IF EXISTS " + NOME_TABELA;


    public PedidoDAO(Context context) {
        super(context);
    }

    @Override
    public String getNomeColunaPrimaryKey() {
        return COLUNA_ID_LOCAL;
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
    public String getNomeColunaEmpresa() {
        return COLUNA_EMP_CODIGO;
    }

    @Override
    public ContentValues entidadeParaContentValues(Pedido pedido) {
        ContentValues values = new ContentValues();
        if(pedido.getId() > 0) {
            values.put(COLUNA_ID_LOCAL, pedido.getId());
        }
        if(pedido.getCodigo_pedido() > 0) {
            values.put(COLUNA_CODIGO_PEDIDO, pedido.getCodigo_pedido());
        }
        values.put(COLUNA_ID_WEB, pedido.getId_web());
        values.put(COLUNA_NUMERO_PEDIDO, pedido.getNumero_pedido());
        values.put(COLUNA_DATA, String.valueOf(pedido.getData().getTime()));
        values.put(COLUNA_ID_VENDEDOR, pedido.getId_vendedor());
        values.put(COLUNA_ID_CLIENTE, pedido.getId_cliente());
        values.put(COLUNA_FORMA_PAGAMENTO, pedido.getFormaPagamento());
        values.put(COLUNA_PRECO_TOTAL, pedido.getPrecoTotal());
        values.put(COLUNA_ATIVO, pedido.isAtivo());
        values.put(COLUNA_EMP_CODIGO, pedido.getEmp_codigo());
        values.put(COLUNA_ENVIADO, pedido.isEnviado());
        values.put(COLUNA_DESCONTO, pedido.getDesconto());


        return values;
    }

    @Override
    public Pedido contentValuesParaEntidade(ContentValues contentValues) {
        Pedido pedido = new Pedido();

        pedido.setId(contentValues.getAsInteger(COLUNA_ID_LOCAL));
        if (contentValues.getAsInteger(COLUNA_ID_WEB) != null) {
            pedido.setId_web(contentValues.getAsInteger(COLUNA_ID_WEB));
        }
        pedido.setAtivo(contentValues.getAsInteger(COLUNA_ATIVO) > 0);
        pedido.setPrecoTotal(contentValues.getAsDouble(COLUNA_PRECO_TOTAL));
        pedido.setFormaPagamento(contentValues.getAsString(COLUNA_FORMA_PAGAMENTO));
        pedido.setId_cliente(contentValues.getAsInteger(COLUNA_ID_CLIENTE));
        pedido.setId_vendedor(contentValues.getAsInteger(COLUNA_ID_VENDEDOR));
        pedido.setData(new Date(Long.valueOf(contentValues.getAsString(COLUNA_DATA))));
        pedido.setCodigo_pedido(contentValues.getAsInteger(COLUNA_CODIGO_PEDIDO));
        pedido.setNumero_pedido(contentValues.getAsString(COLUNA_NUMERO_PEDIDO));
        pedido.setEmp_codigo(contentValues.getAsInteger(COLUNA_EMP_CODIGO));
        pedido.setEnviado(contentValues.getAsInteger(COLUNA_ENVIADO) > 0);
        pedido.setDesconto(contentValues.getAsInteger(COLUNA_DESCONTO));

        return pedido;
    }
}
