package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Usuario;

/**
 * Created by mpire on 19/01/2018.
 */

public class UsuarioDAO {

    private final String TABLE_CLIENTES = "usuarios";

    private DbGateway gw;

    public UsuarioDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(Usuario usuario){

        ContentValues cv = new ContentValues();

        cv.put("login", usuario.getLogin());

        cv.put("senha", usuario.getSenha());

        cv.put("adm", usuario.isAdm());

        cv.put("cadastrar_produtos", usuario.isCadastrarProdutos());

        return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;

    }


}
