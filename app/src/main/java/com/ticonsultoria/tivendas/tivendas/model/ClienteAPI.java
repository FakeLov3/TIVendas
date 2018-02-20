package com.ticonsultoria.tivendas.tivendas.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Helder on 20/02/2018.
 */

public interface ClienteAPI {

    public static final String URL_BASE = "http://cadiizmob.kinghost.net/api/";

    @GET("clientes")
    Call<List<Cliente>> getListaClientes();


}
