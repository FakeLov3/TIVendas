package com.ticonsultoria.tivendas.tivendas.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Helder on 20/02/2018.
 */

public interface UsuarioAPI {

    public static final String BASE_URL = "http://cadizmob.kinghost.net/api/estagio/";

    @GET("usuarios")
    Call<List<Usuario>> getListaUsuarios();
}
