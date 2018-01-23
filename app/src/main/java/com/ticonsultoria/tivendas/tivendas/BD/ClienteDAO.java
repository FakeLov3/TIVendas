package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.ContentValues;
import android.content.Context;

import com.ticonsultoria.tivendas.tivendas.model.Cliente;

/**
 * Created by Helder on 19/01/2018.
 */

public class ClienteDAO {

    private final String TABLE_CLIENTES = "clientes";
    private DbGateway gw;

    public ClienteDAO(Context ctx) {
        this.gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar (Cliente cliente){
        ContentValues cv = new ContentValues();
        cv.put("nome", cliente.getNome());
        cv.put("cpf", cliente.getCpf());
        cv.put("nome_mercado", cliente.getNomeMercado());
        cv.put("ativo", cliente.isAtivo());

        return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
    }
}
