package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mpire on 23/01/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO =  "DBVendas";
    public static final int VERSAO =  1;

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
        //db.execSQL(VeiculoDAO.SCRIPT_CRIACAO_TABELA_VEICULOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(VeiculoDAO.SCRIPT_DELECAO_TABELA);
        //onCreate(db);
    }

}
