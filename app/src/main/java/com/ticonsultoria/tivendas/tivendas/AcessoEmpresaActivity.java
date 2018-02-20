package com.ticonsultoria.tivendas.tivendas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.BD.EmpresaDAO;
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
        
        final EmpresaDAO empresaDao = new EmpresaDAO(this);
        final SharedPreferences[] sharedPreferences = new SharedPreferences[1];

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
                Call<List<Empresa>> call = service.getEmpresa(chave);
                call.enqueue(new Callback<List<Empresa>>() {
                    @Override
                    public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {
                        if (response.body().size() > 0) {
                            Empresa empresa = response.body().get(0);
                            empresaDao.salvar(empresa);



                        } else {
                            Toast.makeText(AcessoEmpresaActivity.this, "Chave de identificação incorreta", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Empresa>> call, Throwable t) {
                        Log.e("tag",t.getMessage());
                    }
                });
            }
        });
    }
}
