package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 19/01/2018.
 */

public class UsuarioDAO {

    private final String TABLE_USUARIOS = "usuarios";

    private DbGateway gw;

    public UsuarioDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(Usuario usuario){
        return salvar(0, usuario);
    }

    public boolean salvar(int id, Usuario usuario){
        ContentValues cv = new ContentValues();
        cv.put("login", usuario.getLogin());
        cv.put("senha", usuario.getSenha());
        cv.put("adm", usuario.isAdm());
        cv.put("cadastrar_produtos", usuario.isCadastrarProdutos());
        cv.put("ativo", usuario.isAtivo());
        if(id > 0)
            return gw.getDatabase().update(TABLE_USUARIOS, cv, "id=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_USUARIOS, null, cv) > 0;
    }



    public List<Usuario> retornarTodos() {

        List<Usuario> usuarios = new ArrayList<>();

        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String login = cursor.getString(cursor.getColumnIndex("login"));
            String senha = cursor.getString(cursor.getColumnIndex("senha"));
            boolean adm = cursor.getInt(cursor.getColumnIndex("adm")) > 0;
            boolean cadastrarProdutos = cursor.getInt(cursor.getColumnIndex("cadastrar_produtos")) > 0;
            boolean ativo = cursor.getInt(cursor.getColumnIndex("ativo")) > 0;
            usuarios.add(new Usuario(id, login, senha, adm, cadastrarProdutos, ativo));

        }

        cursor.close();

        return usuarios;

    }



}
