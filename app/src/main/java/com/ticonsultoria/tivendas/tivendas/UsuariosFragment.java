package com.ticonsultoria.tivendas.tivendas;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerUsuariosAdapter;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {


    RecyclerView mRecyclerView;
    FloatingActionButton floatingActionButton;

    UsuarioDAO dao;

    private RecyclerUsuariosAdapter mAdapter;


    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        dao = new UsuarioDAO(getActivity());

        mRecyclerView = view.findViewById(R.id.rv_usuarios);
        floatingActionButton = view.findViewById(R.id.fab_usuarios_add);
        setupRecycler();

        //Acão do botão flutuante de adicionar
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_usuario, null);

                final RadioGroup radioGroupDialog = dialogView.findViewById(R.id.radio_group_dialog_usuario);

                radioGroupDialog.check(R.id.radio_user);

                builder.setView(dialogView).setTitle("Adicionar usuário")
                        .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Usuario usuario = new Usuario();

                                final EditText edtDialogLogin = dialogView.findViewById(R.id.edt_dialog_usuario_login);
                                final EditText edtDialogSenha = dialogView.findViewById(R.id.edt_dialog_usuario_senha);

                                usuario.setLogin(edtDialogLogin.getText().toString());
                                usuario.setSenha(edtDialogSenha.getText().toString());

                                //Verificar se os campos estão preenchidos
                                if (!usuario.getLogin().equals("") && !usuario.getSenha().equals("")) {

                                    if (radioGroupDialog.getCheckedRadioButtonId() == R.id.radio_adm) {
                                        usuario.setAdm(true);
                                    } else if (radioGroupDialog.getCheckedRadioButtonId() == R.id.radio_user) {
                                        usuario.setAdm(false);
                                    } else {
                                        Toast.makeText(getActivity(),
                                                "Selecione um nível de acesso para o usuário",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    usuario.setCadastrarProdutos(true);
                                    usuario.setAtivo(true);
                                    boolean sucesso = dao.salvar(usuario);

                                    if (sucesso) {
                                        Toast.makeText(getActivity(),
                                                "Usuário cadastrado com sucesso",
                                                Toast.LENGTH_SHORT).show();

                                        mAdapter.updateList(usuario);
                                    }


                                } else { //Campos não preenchidos
                                    Toast.makeText(getActivity(),
                                            "Preencha todos os campos para adicionar um usuário",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }


    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<Usuario> array = new ArrayList<>(dao.retornarTodos());

        mAdapter = new RecyclerUsuariosAdapter(array, getContext());
        mRecyclerView.setAdapter(mAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

}
