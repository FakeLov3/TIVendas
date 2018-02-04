package com.ticonsultoria.tivendas.tivendas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin;
    EditText edtSenha;
    Button btnLogar;

    UsuarioDAO dao;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edt_login_login);
        edtSenha = findViewById(R.id.edt_login_senha);
        btnLogar = findViewById(R.id.btn_login);

        sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        dao = new UsuarioDAO(this);

        final List<Usuario> usuarios = dao.recuperarAtivos();

        int userId = sharedPreferences.getInt("user_id",0);

        for (Usuario usuario: usuarios) {
            if (usuario.getId() == userId) {
                logar(usuario);
            }
        }

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                if (login.equals("") || senha.equals("")) {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos para continuar", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                boolean validado = false;

                for (int i = 0; i < usuarios.size(); i++) {
                    Usuario user = usuarios.get(i);

                    if (user.getLogin().equals(login)) { //checar login

                        if (user.getSenha().equals(senha)) { //checar senha
                            logar(user);
                            validado = true;
                        }
                    
                    }

                }
                
                if (!validado) {
                    Toast.makeText(LoginActivity.this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void logar(Usuario usuario) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", usuario.getId());
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
