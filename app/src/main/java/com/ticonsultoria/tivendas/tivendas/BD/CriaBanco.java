package com.ticonsultoria.tivendas.tivendas.BD;

/**
 * Created by mpire on 19/01/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allanromanato on 5/27/15.
 */
public class CriaBanco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "TIVendas1.db";


    private static final String TABELA_USUARIOS = "usuarios";
    private static final String ID_USUARIOS = "_id";
    private static final String LOGIN_USUARIOS = "login";
    private static final String SENHA_USUARIOS = "senha";
    private static final String ADM_USUARIOS = "adm";
    private static final String CADASTRAR_PRODUTOS_USUARIO = "cadastrar_produtos";
    private static final String ATIVO_USUARIO = "ativo";

    private static final String TABELA_CLIENTES = "";


    private static final int VERSAO = 1;

    public CriaBanco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsuarios = "CREATE TABLE "+TABELA_USUARIOS+"("
                + ID_USUARIOS + " integer primary key autoincrement,"
                + LOGIN_USUARIOS + " VARCHAR,"
                + SENHA_USUARIOS + " VARCHAR,"
                + ADM_USUARIOS + " BOOLEAN,"
                + CADASTRAR_PRODUTOS_USUARIO + " BOOLEAN,"
                + ATIVO_USUARIO + " BOOLEAN"
                +")";

        db.execSQL(sqlUsuarios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}