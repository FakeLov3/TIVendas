package com.ticonsultoria.tivendas.tivendas.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Helder on 16/02/2018.
 */

public interface EmpresaAPI {

    public static final String URL_BASE = "cadizmob.kinghost.net/api/estagio/";

    @GET("empresas")
    Call<List<Empresa>> getListaEmpresas();

    @GET("empresas/chave/{chave}")
    Call<Empresa> getEmpresa(@Path("chave") String chave);




}
