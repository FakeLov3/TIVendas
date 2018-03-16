package com.ticonsultoria.tivendas.tivendas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.BD.EmpresaDAO;
import com.ticonsultoria.tivendas.tivendas.BD.SumarioDAO;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.toDate;
import com.ticonsultoria.tivendas.tivendas.model.Empresa;
import com.ticonsultoria.tivendas.tivendas.model.EmpresaAPI;
import com.ticonsultoria.tivendas.tivendas.model.Sumario;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;
import com.ticonsultoria.tivendas.tivendas.model.UsuarioAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcessoEmpresaActivity extends AppCompatActivity {

    private EmpresaDAO empresaDao;
    private UsuarioDAO usuarioDao;
    private SumarioDAO sumarioDao;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_empresa);
        
        empresaDao = new EmpresaDAO(this);
        usuarioDao = new UsuarioDAO(this);
        sumarioDao = new SumarioDAO(this);

        sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        verificarEmpresa();

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

                if (chave.equals("")){
                    Toast.makeText(AcessoEmpresaActivity.this, "Insira uma chave", Toast.LENGTH_SHORT).show();
                    return;
                }

                Call<List<Empresa>> call = service.getEmpresa(chave);
                call.enqueue(new Callback<List<Empresa>>() {
                    @Override
                    public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {
                        if (response.body().size() > 0) {
                            Empresa empresa = response.body().get(0);
                            baixarUsuarios(empresa);
                            acessar(empresa);
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

    private void baixarUsuarios(final Empresa empresa){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsuarioAPI.BASE_URL + empresa.getEmp_codigo() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioAPI service = retrofit.create(UsuarioAPI.class);

        Call<List<Usuario>> call = service.getListaUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                Date lastSync = null;

                if (response.body().size() > 0){
                    lastSync = toDate.from(response.body().get(0).getLast_sync());
                }

                for (int i =0; i<response.body().size(); i++){
                    usuarioDao.salvar(response.body().get(i));
                    Date data = toDate.from(response.body().get(i).getLast_sync());
                    if (lastSync.before(data)){
                        lastSync = data;
                    }
                }

                Sumario sumario = new Sumario();

                sumario.setNomeTabela(UsuarioDAO.NOME_TABELA);
                sumario.setEmp_codigo(empresa.getEmp_codigo());
                sumario.setLastSync(lastSync.toString());

                sumarioDao.salvar(sumario);

                String lastSyncUsuarios = sumarioDao.getLastSyncUsuarios();

                Log.e("LastSyncUsuarios", lastSyncUsuarios);

                List<Usuario> usuarios = usuarioDao.recuperarAtivos();

                for (Usuario usuario : usuarios){
                    Log.e("CADASTRAR PRODUTOS: ", usuario.isCadastrarProdutos());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void verificarEmpresa() {
        String chaveEmpresa = sharedPreferences.getString("chave_empresa","");
        if (!chaveEmpresa.equals("")) {
            Intent intent = new Intent(AcessoEmpresaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void acessar(Empresa empresa){
        empresaDao.salvar(empresa);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("chave_empresa", empresa.getEmp_chave());
        editor.putInt("id_empresa", empresa.getEmp_codigo());
        editor.commit();
        Intent intent = new Intent(AcessoEmpresaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
