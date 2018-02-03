package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mpire on 23/01/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO =  "DBVendas";
    public static final int VERSAO =  8;

    private static DataBaseHelper instance;

    private DataBaseHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    public static DataBaseHelper getInstance(Context context) {
        if(instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProdutoDAO.SCRIPT_CRIACAO_TABELA_PRODUTOS);
        db.execSQL(ClienteDAO.SCRIPT_CRIACAO_TABELA_CLIENTES);
        db.execSQL(UsuarioDAO.SCRIPT_CRIACAO_TABELA_USUARIOS);
        db.execSQL(PedidoDAO.SCRIPT_CRIACAO_TABELA_PEDIDOS);
        db.execSQL(ItemDAO.SCRIPT_CRIACAO_TABELA_ITENS);
        Log.i("DATABASE", "CRIANDO TABELA");


        //precisar ser usado após a criação da tabela usuários
        db.execSQL(UsuarioDAO.SCRIPT_INSERCAO_USUARIO_PADRAO);
        Log.i("DATABASE", "USUÁRIO PADRÃO CRIADO");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DATABASE", "ATUALIZANDO TABELA");
        db.execSQL(ProdutoDAO.SCRIPT_DELECAO_TABELA_PRODUTOS);
        db.execSQL(ClienteDAO.SCRIPT_DELECAO_TABELA_CLIENTES);
        db.execSQL(UsuarioDAO.SCRIPT_DELECAO_TABELA_USUARIOS);
        db.execSQL(PedidoDAO.SCRIPT_DELECAO_TABELA_PEDIDOS);
        db.execSQL(ItemDAO.SCRIPT_DELECAO_TABELA_ITENS);
        onCreate(db);
    }

}
