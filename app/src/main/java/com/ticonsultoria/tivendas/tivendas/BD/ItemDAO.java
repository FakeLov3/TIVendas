package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Item;

import java.util.List;

/**
 * Created by mpire on 28/01/2018.
 */

public class ItemDAO extends DAOBasico<Item> {

    public static final String NOME_TABELA = "itens";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_ID_PRODUTO = "id_produto";
    public static final String COLUNA_ID_PEDIDO = "id_pedido";
    public static final String COLUNA_QUANTIDADE = "quantidade";


    public static final String SCRIPT_CRIACAO_TABELA_ITENS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_ID_PRODUTO + " INTEGER,"
            + COLUNA_ID_PEDIDO + " INTEGER,"
            + COLUNA_QUANTIDADE + " INTEGER"
            + ")";

    public static final String SCRIPT_DELECAO_TABELA_ITENS =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    public ItemDAO(Context context) {
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
        return null;
    }

    public List<Item> recuperarItensPedido(int idPedido) {

        String query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_ID_PEDIDO + " = "
                + idPedido;

        return super.recuperarPorQuery(query);
    }

    @Override
    public ContentValues entidadeParaContentValues(Item item) {
        ContentValues values = new ContentValues();
        if(item.getId() > 0) {
            values.put(COLUNA_ID, item.getId());
        }
        if(item.getId_pedido() > 0) {
            values.put(COLUNA_ID_PEDIDO, item.getId_pedido());
        }
        if(item.getId_produto() > 0) {
            values.put(COLUNA_ID_PRODUTO, item.getId_produto());
        }
        if(item.getQuantidade() > 0) {
            values.put(COLUNA_QUANTIDADE, item.getQuantidade());
        }

        return values;
    }

    @Override
    public Item contentValuesParaEntidade(ContentValues contentValues) {
        Item item = new Item();

        item.setId(contentValues.getAsInteger(COLUNA_ID));
        item.setId_pedido(contentValues.getAsInteger(COLUNA_ID_PEDIDO));
        item.setId_produto(contentValues.getAsInteger(COLUNA_ID_PRODUTO));
        item.setQuantidade(contentValues.getAsInteger(COLUNA_QUANTIDADE));

        return item;
    }
}
