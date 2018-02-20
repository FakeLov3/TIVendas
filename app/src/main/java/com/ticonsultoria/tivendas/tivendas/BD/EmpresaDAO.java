package com.ticonsultoria.tivendas.tivendas.BD;

/**
 * Created by mpire on 16/02/2018.
 */

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Empresa;


/**
 * Created by mpire on 19/01/2018.
 */

public class EmpresaDAO extends DAOBasico<Empresa> {

    public static final String NOME_TABELA = "empresas";
    public static final String COLUNA_ID_LOCAL = "id";
    public static final String COLUNA_CODIGO = "emp_codigo";
    public static final String COLUNA_RAZAO = "em_razao";
    public static final String COLUNA_FANTASIA = "emp_fantasia";
    public static final String COLUNA_CNPJ = "emp_cnpj";
    public static final String COLUNA_CHAVE = "emp_chave";


    public static final String SCRIPT_CRIACAO_TABELA_EMPRESAS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID_LOCAL + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_CODIGO + " INTEGER,"
            + COLUNA_RAZAO + " TEXT,"
            + COLUNA_FANTASIA + " TEXT,"
            + COLUNA_CNPJ + " TEXT,"
            + COLUNA_CHAVE + " TEXT"
            + ")";

    public static final String SCRIPT_DELECAO_TABELA_EMPRESAS =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    public EmpresaDAO (Context context) {
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
        return null;
    }

    @Override
    public ContentValues entidadeParaContentValues(Empresa empresa) {
        ContentValues values = new ContentValues();
        if(empresa.getId() > 0) {
            values.put(COLUNA_ID_LOCAL, empresa.getId());
        }
        values.put(COLUNA_CHAVE, empresa.getEmp_chave());
        values.put(COLUNA_CNPJ, empresa.getEmp_cnpj());
        values.put(COLUNA_CODIGO, empresa.getEmp_codigo());
        values.put(COLUNA_FANTASIA, empresa.getEmp_fantasia());
        values.put(COLUNA_RAZAO, empresa.getEmp_razao());

        return values;
    }

    @Override
    public Empresa contentValuesParaEntidade(ContentValues contentValues) {

        Empresa empresa = new Empresa();

        empresa.setId(contentValues.getAsInteger(COLUNA_ID_LOCAL));

        if (contentValues.getAsInteger(COLUNA_CODIGO) != null) {
            empresa.setEmp_codigo(contentValues.getAsInteger(COLUNA_CODIGO));
        }

        empresa.setEmp_chave(contentValues.getAsString(COLUNA_CHAVE));
        empresa.setEmp_fantasia(contentValues.getAsString(COLUNA_FANTASIA));
        empresa.setEmp_cnpj(contentValues.getAsString(COLUNA_CNPJ));
        empresa.setEmp_razao(contentValues.getAsString(COLUNA_RAZAO));

        return empresa;
    }

}
