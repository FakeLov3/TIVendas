package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ticonsultoria.tivendas.tivendas.AcessoEmpresaActivity;
import com.ticonsultoria.tivendas.tivendas.LoginActivity;
import com.ticonsultoria.tivendas.tivendas.model.EntidadePersistivel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Helder on 23/01/2018.
 */

public abstract class DAOBasico <T extends EntidadePersistivel> {

    protected SQLiteDatabase dataBase = null;
    SharedPreferences sharedPreferences;

    public DAOBasico(Context context){
        sharedPreferences = context.
                getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        DataBaseHelper persistenceHelper = DataBaseHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }

    public abstract String getNomeColunaPrimaryKey();
    public abstract String getNomeTabela();
    public abstract String getNomeColunaAtivo();
    public abstract String getNomeColunaEmpresa();

    public abstract ContentValues entidadeParaContentValues(T entidade);
    public abstract T contentValuesParaEntidade(ContentValues contentValues);

    public long salvar(T entidade){
        ContentValues values = entidadeParaContentValues(entidade);
        return dataBase.insert(getNomeTabela(), null, values);
    }

    public void deletar(T t){
        String[] valoresParaSubstituir = {
                String.valueOf(t.getId())
        };
        dataBase.delete(getNomeTabela(), getNomeColunaPrimaryKey() + " =  ?", valoresParaSubstituir);
    }

    public void editar(T t) {
        ContentValues valores = entidadeParaContentValues(t);
        String[] valoresParaSubstituir = {
            String.valueOf(t.getId())
        };
        dataBase.update(getNomeTabela(), valores, getNomeColunaPrimaryKey() +"= ?", valoresParaSubstituir  );
    }

    public List<T> recuperarTodos() {
        int idEmpresa = sharedPreferences.getInt("id_empresa",0);
        String queryReturnAll = "SELECT * FROM " + getNomeTabela() + " WHERE " + getNomeColunaEmpresa() + " = " + idEmpresa;
        List<T> result = recuperarPorQuery(queryReturnAll);

        return result;
    }

    public List<T> recuperarAtivos() {
        int idEmpresa = sharedPreferences.getInt("id_empresa",0);
        String queryReturnAll = "SELECT * FROM " + getNomeTabela() + " WHERE " + getNomeColunaAtivo() + " = 'S' AND "
                + getNomeColunaEmpresa() + " = " + idEmpresa;
        List<T> result = recuperarPorQuery(queryReturnAll);

        return result;
    }

    public T recuperarPorID(int id) {
        String queryOne = "SELECT * FROM " + getNomeTabela() + " where " + getNomeColunaPrimaryKey() + " = " + id;
        List<T> result = recuperarPorQuery(queryOne);
        if(result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public List<T> recuperarPorQuery(String query) {

        Cursor cursor = dataBase.rawQuery(query, null);

        List<T> result = new ArrayList<T>();
        if (cursor.moveToFirst()) {
            do {
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
                T t = contentValuesParaEntidade(contentValues);
                result.add(t);
            } while (cursor.moveToNext());
        }
        return result;

    }

    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen())
            dataBase.close();
    }

}
