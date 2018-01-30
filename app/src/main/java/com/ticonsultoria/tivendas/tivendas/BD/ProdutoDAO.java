package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Produto;

/**
 * Created by Helder on 23/01/2018.
 */

public class ProdutoDAO extends DAOBasico<Produto> {

    public static final String NOME_TABELA = "produtos";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME_PRODUTO = "nome_produto";
    public static final String COLUNA_PRECO = "preco";
    public static final String COLUNA_CATEGORIA = "categoria";
    public static final String COLUNA_FORNECEDOR = "fornecedor";
    public static final String COLUNA_MARCA = "marca";
    public static final String COLUNA_QUANTIDADE = "quantidade";
    public static final String COLUNA_ATIVO = "ativo";
    public static final String COLUNA_FOTO = "foto";

    public static final String SCRIPT_CRIACAO_TABELA_PRODUTOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_NOME_PRODUTO + " TEXT,"
            + COLUNA_PRECO + " DOUBLE,"
            + COLUNA_CATEGORIA + " TEXT,"
            + COLUNA_FORNECEDOR + " TEXT,"
            + COLUNA_MARCA + " TEXT,"
            + COLUNA_QUANTIDADE + " INT,"
            + COLUNA_ATIVO + " BOOLEAN,"
            + COLUNA_FOTO + " BLOB"
            + ")";

    public static final String SCRIPT_DELECAO_TABELA_PRODUTOS =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    private static ProdutoDAO instance;

    public static ProdutoDAO getInstance(Context context) {
        if(instance == null)
            instance = new ProdutoDAO(context);
        return instance;
    }

    public ProdutoDAO(Context context) {
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
    public ContentValues entidadeParaContentValues(Produto produto) {
        ContentValues values = new ContentValues();
        if(produto.getId() > 0) {
            values.put(COLUNA_ID, produto.getId());
        }
        values.put(COLUNA_NOME_PRODUTO, produto.getNome_produto());
        values.put(COLUNA_PRECO, produto.getPreco());
        values.put(COLUNA_CATEGORIA, produto.getCategoria());
        values.put(COLUNA_FORNECEDOR, produto.getFornecedor());
        values.put(COLUNA_MARCA, produto.getMarca());
        values.put(COLUNA_QUANTIDADE, produto.getQuantidade());
        values.put(COLUNA_ATIVO, produto.isAtivo());
        values.put(COLUNA_FOTO, produto.getFoto());

        return values;
    }

    @Override
    public Produto contentValuesParaEntidade(ContentValues contentValues) {
        Produto produto = new Produto();
        produto.setId(contentValues.getAsInteger(COLUNA_ID));
        produto.setAtivo(contentValues.getAsInteger(COLUNA_ATIVO) > 0);
        produto.setQuantidade(contentValues.getAsInteger(COLUNA_QUANTIDADE));
        produto.setMarca(contentValues.getAsString(COLUNA_MARCA));
        produto.setFornecedor(contentValues.getAsString(COLUNA_FORNECEDOR));
        produto.setCategoria(contentValues.getAsString(COLUNA_CATEGORIA));
        produto.setPreco(contentValues.getAsDouble(COLUNA_PRECO));
        produto.setNome_produto(contentValues.getAsString(COLUNA_NOME_PRODUTO));
        produto.setFoto(contentValues.getAsByteArray(COLUNA_FOTO));
        return produto;
    }




}
