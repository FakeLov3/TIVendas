package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Sumario;

import java.util.Date;
import java.util.List;

/**
 * Created by Helder on 27/02/2018.
 */

public class SumarioDAO extends DAOBasico<Sumario> {
    public static final String NOME_TABELA = "sumario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME_TABELA = "nome_tabla";
    public static final String COLUNA_LAST_SYNC = "last_sync";
    public static final String COLUNA_EMP_CODIGO = "emp_codigo";

    public static final String SCRIPT_CRIACAO_TABELA_SUMARIO = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_NOME_TABELA + " TEXT,"
            + COLUNA_LAST_SYNC + " TEXT,"
            + COLUNA_EMP_CODIGO + " INTEGER"
            + ")";


    public static final String SCRIPT_DELECAO_TABELA_SUMARIO =  "DROP TABLE IF EXISTS " + NOME_TABELA;



    public SumarioDAO (Context context) {
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

    @Override
    public String getNomeColunaEmpresa() {
        return COLUNA_EMP_CODIGO;
    }

    @Override
    public ContentValues entidadeParaContentValues(Sumario sumario) {
        ContentValues values = new ContentValues();
        if(sumario.getId() > 0) {
            values.put(COLUNA_ID, sumario.getId());
        }
        values.put(COLUNA_NOME_TABELA, sumario.getId());
        values.put(COLUNA_EMP_CODIGO, sumario.getEmp_codigo());
        values.put(COLUNA_LAST_SYNC, String.valueOf(sumario.getLastSync()));

        return values;
    }

    @Override
    public Sumario contentValuesParaEntidade(ContentValues contentValues) {
        Sumario sumario = new Sumario();

        sumario.setId(contentValues.getAsInteger(COLUNA_ID));

        if (contentValues.getAsInteger(COLUNA_EMP_CODIGO) != null) {
            sumario.setEmp_codigo(contentValues.getAsInteger(COLUNA_EMP_CODIGO));
        }
        sumario.setNomeTabela(contentValues.getAsString(COLUNA_NOME_TABELA));
        sumario.setLastSync(contentValues.getAsString(COLUNA_LAST_SYNC));

        return sumario;
    }

    public String getLastSyncUsuarios(){
        List<Sumario> sumarios = super.recuperarTodos();
        for(Sumario sumario: sumarios){
            if (sumario.getNomeTabela().equals(UsuarioDAO.NOME_TABELA)){
                return String.valueOf(sumario.getLastSync());
            }
        }
        return null;
    }

    @Override
    public long salvar(Sumario sumario) {
        List<Sumario> sumarios = super.recuperarPorQuery("SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_ID + " = '" + sumario.getId() + "'");

        if (sumarios.isEmpty()) {
            return super.salvar(sumario);
        } else {
            return 0;
        }

    }
}
