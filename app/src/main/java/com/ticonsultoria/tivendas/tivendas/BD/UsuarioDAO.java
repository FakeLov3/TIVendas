package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.List;


/**
 * Created by mpire on 19/01/2018.
 */

public class UsuarioDAO extends DAOBasico<Usuario> {

    public static final String NOME_TABELA = "usuarios";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_LOGIN = "login";
    public static final String COLUNA_SENHA = "senha";
    public static final String COLUNA_ADM = "adm";
    public static final String COLUNA_CADASTRAR_PRODUTOS = "cadastrar_produtos";
    public static final String COLUNA_ATIVO = "ativo";


    public static final String SCRIPT_CRIACAO_TABELA_USUARIOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_LOGIN + " TEXT,"
            + COLUNA_SENHA + " TEXT,"
            + COLUNA_ADM + " BOOLEAN,"
            + COLUNA_CADASTRAR_PRODUTOS + " BOOLEAN,"
            + COLUNA_ATIVO + " BOOLEAN"
            + ")";

    public static final String SCRIPT_DELECAO_TABELA_USUARIOS =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    private static UsuarioDAO instance;

    public static UsuarioDAO getInstance(Context context) {
        if(instance == null)
            instance = new UsuarioDAO(context);
        return instance;
    }

    public UsuarioDAO (Context context) {
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
    public ContentValues entidadeParaContentValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        if(usuario.getId() > 0) {
            values.put(COLUNA_ID, usuario.getId());
        }
        values.put(COLUNA_LOGIN, usuario.getLogin());
        values.put(COLUNA_SENHA, usuario.getSenha());
        values.put(COLUNA_ADM, usuario.isAdm());
        values.put(COLUNA_CADASTRAR_PRODUTOS, usuario.isCadastrarProdutos());
        values.put(COLUNA_ATIVO, usuario.isAtivo());

        return values;
    }

    @Override
    public Usuario contentValuesParaEntidade(ContentValues contentValues) {
        Usuario usuario = new Usuario();

        usuario.setId(contentValues.getAsInteger(COLUNA_ID));
        usuario.setLogin(contentValues.getAsString(COLUNA_LOGIN));
        usuario.setSenha(contentValues.getAsString(COLUNA_SENHA));
        usuario.setAdm(contentValues.getAsInteger(COLUNA_ADM) > 0);
        usuario.setCadastrarProdutos(contentValues.getAsInteger(COLUNA_CADASTRAR_PRODUTOS) > 0);
        usuario.setAtivo(contentValues.getAsInteger(COLUNA_ATIVO) > 0);

        return usuario;
    }

    @Override
    public long salvar(Usuario entidade) {
        List<Usuario> usuarios = super.recuperarPorQuery("SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_LOGIN + " = '" + entidade.getLogin() + "'");

        if (usuarios.isEmpty()) {
            return super.salvar(entidade);
        } else {
            return 0;
        }

    }
}
