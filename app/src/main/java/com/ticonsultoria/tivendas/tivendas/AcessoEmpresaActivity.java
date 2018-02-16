package com.ticonsultoria.tivendas.tivendas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ticonsultoria.tivendas.tivendas.model.Empresa;
import com.ticonsultoria.tivendas.tivendas.model.EmpresaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcessoEmpresaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_empresa);

        Button botao = findViewById(R.id.button);
        final EditText editChave = findViewById(R.id.editText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EmpresaAPI.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final EmpresaAPI service = retrofit.create(EmpresaAPI.class);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chave = editChave.getText().toString();
                Call<Empresa> listCall = service.getEmpresa(chave);
                listCall.enqueue(new Callback<Empresa>() {
                    @Override
                    public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                        Log.i("tag",response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Empresa> call, Throwable t) {
                        Log.e("tag",t.getMessage());
                    }
                });
            }
        });
    }
}
