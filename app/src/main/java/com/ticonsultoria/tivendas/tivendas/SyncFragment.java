package com.ticonsultoria.tivendas.tivendas;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ticonsultoria.tivendas.tivendas.BD.SumarioDAO;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.toDate;
import com.ticonsultoria.tivendas.tivendas.model.Sumario;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;
import com.ticonsultoria.tivendas.tivendas.model.UsuarioAPI;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SyncFragment extends Fragment {

    SharedPreferences sharedPreferences;
    int empresaId;

    SumarioDAO sumarioDAO;
    UsuarioDAO usuarioDAO;

    public SyncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sync, container, false);

        Button btnEnviar = view.findViewById(R.id.btn_enviar);
        Button btnBaixar = view.findViewById(R.id.btn_baixar);

        sumarioDAO = new SumarioDAO(getContext());
        usuarioDAO = new UsuarioDAO(getContext());

        sharedPreferences = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        empresaId = sharedPreferences.getInt("id_empresa",0);

        //Baixar dados
        btnBaixar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                atualizarTabelaUsuarios();
                atualizarTabelaClientes();

            }
        });

        //Enviar dados
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });



        return view;
    }


    private void atualizarTabelaUsuarios(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsuarioAPI.BASE_URL + empresaId + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioAPI service = retrofit.create(UsuarioAPI.class);

        String lastSyncUsuarios = sumarioDAO.getLastSyncUsuarios();

        Call<List<Usuario>> call = service.getListaUsuariosByData(lastSyncUsuarios);

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Date lastSync;

                if (response.body().size() > 0){
                    lastSync = toDate.from(response.body().get(0).getLast_sync());
                } else {
                    return;
                }

                for (int i =0; i<response.body().size(); i++){
                    usuarioDAO.salvar(response.body().get(i));
                    Date data = toDate.from(response.body().get(i).getLast_sync());
                    if (lastSync.before(data)){
                        lastSync = data;
                    }
                }

                Sumario sumario = new Sumario();

                int empCodigo = sharedPreferences.getInt("id_empresa",0);

                sumario.setNomeTabela(UsuarioDAO.NOME_TABELA);
                sumario.setEmp_codigo(empCodigo);
                sumario.setLastSync(toDate.string(lastSync));

                sumarioDAO.salvar(sumario);

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("ERRO",t.getMessage());
            }
        });
    }
    private void atualizarTabelaClientes(){

    };

    private void atualizarProdutos(){

    };


}
