package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;
import android.util.Base64;

import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.Date;
import java.util.List;


/**
 * Created by mpire on 19/01/2018.
 */

public class UsuarioDAO extends DAOBasico<Usuario> {

    public static final String NOME_TABELA = "usuarios";
    public static final String COLUNA_ID_LOCAL = "id_local";
    public static final String COLUNA_ID_WEB = "id_web";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_TELEFONE = "telefone";
    public static final String COLUNA_IMAGEM = "imagem_usuario";
    public static final String COLUNA_LOGIN = "login";
    public static final String COLUNA_SENHA = "senha";
    public static final String COLUNA_ADM = "adm";
    public static final String COLUNA_CADASTRAR_PRODUTOS = "cadastrar_produtos";
    public static final String COLUNA_ATIVO = "ativo";
    public static final String COLUNA_EMP_CODIGO = "emp_codigo";
    public static final String COLUNA_LAST_SYNC = "last_sync";

    private static int codigoUsuarioPadrao = 255423165;

    public static final String SCRIPT_CRIACAO_TABELA_USUARIOS = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID_LOCAL + " INTEGER PRIMARY KEY autoincrement,"
            + COLUNA_ID_WEB + " INTEGER,"
            + COLUNA_NOME + " TEXT,"
            + COLUNA_EMAIL + " TEXT,"
            + COLUNA_TELEFONE + " TEXT,"
            + COLUNA_IMAGEM + " BLOB,"
            + COLUNA_LOGIN + " TEXT,"
            + COLUNA_SENHA + " TEXT,"
            + COLUNA_ADM + " BOOLEAN,"
            + COLUNA_CADASTRAR_PRODUTOS + " BOOLEAN,"
            + COLUNA_ATIVO + " BOOLEAN,"
            + COLUNA_EMP_CODIGO + " INTEGER,"
            + COLUNA_LAST_SYNC + " TEXT"
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
    public ContentValues entidadeParaContentValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        if(usuario.getId() > 0) {
            values.put(COLUNA_ID_LOCAL, usuario.getId());
        }
        values.put(COLUNA_ID_WEB, usuario.getId_web());
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_EMAIL, usuario.getEmail());
        values.put(COLUNA_TELEFONE, usuario.getTelefone());
        values.put(COLUNA_IMAGEM, usuario.getImagem_usuario_bytes());
        values.put(COLUNA_LOGIN, usuario.getLogin());
        values.put(COLUNA_SENHA, usuario.getSenha());
        values.put(COLUNA_ADM, usuario.isAdm());
        values.put(COLUNA_CADASTRAR_PRODUTOS, usuario.isCadastrarProdutos());
        values.put(COLUNA_ATIVO, usuario.isAtivo());
        values.put(COLUNA_EMP_CODIGO, usuario.getEmp_codigo());
        values.put(COLUNA_LAST_SYNC, String.valueOf(usuario.getLast_sync()));

        return values;
    }

    @Override
    public Usuario contentValuesParaEntidade(ContentValues contentValues) {
        Usuario usuario = new Usuario();

        usuario.setId(contentValues.getAsInteger(COLUNA_ID_LOCAL));

        if (contentValues.getAsInteger(COLUNA_ID_WEB) != null) {
            usuario.setId_web(contentValues.getAsInteger(COLUNA_ID_WEB));
        }

        usuario.setNome(contentValues.getAsString(COLUNA_NOME));
        usuario.setEmail(contentValues.getAsString(COLUNA_EMAIL));
        usuario.setTelefone(contentValues.getAsString(COLUNA_TELEFONE));
        usuario.setImagem_usuario_bytes(contentValues.getAsByteArray(COLUNA_IMAGEM));
        usuario.setLogin(contentValues.getAsString(COLUNA_LOGIN));
        usuario.setSenha(contentValues.getAsString(COLUNA_SENHA));
        usuario.setAdm(contentValues.getAsString(COLUNA_ADM));
        usuario.setCadastrarProdutos(contentValues.getAsString(COLUNA_CADASTRAR_PRODUTOS));
        usuario.setAtivo(contentValues.getAsString(COLUNA_ATIVO));
        usuario.setEmp_codigo(contentValues.getAsInteger(COLUNA_EMP_CODIGO));
        usuario.setLast_sync(contentValues.getAsString(COLUNA_LAST_SYNC));

        return usuario;
    }

    @Override
    public long salvar(Usuario entidade) {
        List<Usuario> usuarios = super.recuperarPorQuery("SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_LOGIN + " = '" + entidade.getLogin() + "'");

        if (!entidade.getImagem_usuario().equals("")){
            entidade.setImagem_usuario_bytes(Base64.decode(entidade.getImagem_usuario(), Base64.DEFAULT));
        }

        if (usuarios.isEmpty()) {
            return super.salvar(entidade);
        } else {
            return 0;
        }

    }

    @Override
    public List<Usuario> recuperarAtivos() {
        int idEmpresa = sharedPreferences.getInt("id_empresa",0);
        String queryReturnAll = "SELECT * FROM " + getNomeTabela() + " WHERE " + getNomeColunaAtivo() + " = 'S' AND ("
                + getNomeColunaEmpresa() + " = " + idEmpresa + " OR " + getNomeColunaEmpresa() + " = '" + codigoUsuarioPadrao + "')";
        return recuperarPorQuery(queryReturnAll);
    }
}
