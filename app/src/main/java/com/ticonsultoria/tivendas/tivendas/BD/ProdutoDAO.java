package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Produto;

/**
 * Created by Helder on 23/01/2018.
 */

public class ProdutoDAO extends DAOBasico<Produto> {

    public static final String NOME_TABELA = "produtos";
    public static final String COLUNA_ID_LOCAL = "id_local";
    public static final String COLUNA_ID_WEB = "id_web";
    public static final String COLUNA_NOME_PRODUTO = "nome_produto";
    public static final String COLUNA_PRECO = "preco";
    public static final String COLUNA_CATEGORIA = "categoria";
    public static final String COLUNA_FORNECEDOR = "fornecedor";
    public static final String COLUNA_MARCA = "marca";
    public static final String COLUNA_QUANTIDADE = "quantidade";
    public static final String COLUNA_ATIVO = "ativo";
    public static final String COLUNA_FOTO = "foto";
    public static final String COLUNA_EMP_CODIGO = "emp_codigo";

    public static final String SCRIPT_CRIACAO_TABELA_PRODUTOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID_LOCAL + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_ID_WEB + " INTEGER,"
            + COLUNA_NOME_PRODUTO + " TEXT,"
            + COLUNA_PRECO + " DOUBLE,"
            + COLUNA_CATEGORIA + " TEXT,"
            + COLUNA_FORNECEDOR + " TEXT,"
            + COLUNA_MARCA + " TEXT,"
            + COLUNA_QUANTIDADE + " INT,"
            + COLUNA_ATIVO + " BOOLEAN,"
            + COLUNA_FOTO + " BLOB,"
            + COLUNA_EMP_CODIGO + " INTEGER"
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
    public ContentValues entidadeParaContentValues(Produto produto) {
        ContentValues values = new ContentValues();
        if(produto.getId() > 0) {
            values.put(COLUNA_ID_LOCAL, produto.getId());
        }
        values.put(COLUNA_ID_WEB, produto.getId_web());
        values.put(COLUNA_NOME_PRODUTO, produto.getNome_produto());
        values.put(COLUNA_PRECO, produto.getPreco());
        values.put(COLUNA_CATEGORIA, produto.getCategoria());
        values.put(COLUNA_FORNECEDOR, produto.getFornecedor());
        values.put(COLUNA_MARCA, produto.getMarca());
        values.put(COLUNA_QUANTIDADE, produto.getQuantidade());
        values.put(COLUNA_ATIVO, produto.isAtivo());
        values.put(COLUNA_FOTO, produto.getFoto());
        values.put(COLUNA_EMP_CODIGO, produto.getEmp_codigo());

        return values;
    }

    @Override
    public Produto contentValuesParaEntidade(ContentValues contentValues) {
        Produto produto = new Produto();
        produto.setId(contentValues.getAsInteger(COLUNA_ID_LOCAL));
        if (contentValues.getAsInteger(COLUNA_ID_WEB) != null) {
            produto.setId_web(contentValues.getAsInteger(COLUNA_ID_WEB));
        }
        produto.setAtivo(contentValues.getAsInteger(COLUNA_ATIVO) > 0);
        produto.setQuantidade(contentValues.getAsInteger(COLUNA_QUANTIDADE));
        produto.setMarca(contentValues.getAsString(COLUNA_MARCA));
        produto.setFornecedor(contentValues.getAsString(COLUNA_FORNECEDOR));
        produto.setCategoria(contentValues.getAsString(COLUNA_CATEGORIA));
        produto.setPreco(contentValues.getAsDouble(COLUNA_PRECO));
        produto.setNome_produto(contentValues.getAsString(COLUNA_NOME_PRODUTO));
        produto.setFoto(contentValues.getAsByteArray(COLUNA_FOTO));
        produto.setEmp_codigo(contentValues.getAsInteger(COLUNA_EMP_CODIGO));
        return produto;
    }




}
