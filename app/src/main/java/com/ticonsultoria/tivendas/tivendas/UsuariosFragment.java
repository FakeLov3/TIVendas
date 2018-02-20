package com.ticonsultoria.tivendas.tivendas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerUsuariosAdapter;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomDeleteClickListener;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomEditClickListener;
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

    ImageView imageView;

    public UsuariosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_CANCELED){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            int nh = (int) (bitmap.getHeight()* (512.0 / bitmap.getWidth()));
            Bitmap bitmapaux = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            imageView.setImageBitmap(bitmapaux);
        }

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

                RadioButton radioButtonAdm = dialogView.findViewById(R.id.radio_adm);
                RadioButton radioButtonUser = dialogView.findViewById(R.id.radio_user);

                final Switch cadastrarProdutos = dialogView.findViewById(R.id.sw_cadastrar_produtos);

                radioButtonAdm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cadastrarProdutos.setChecked(true);
                        cadastrarProdutos.setEnabled(false);
                        cadastrarProdutos.setVisibility(Switch.INVISIBLE);
                    }
                });

                radioButtonUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cadastrarProdutos.setChecked(false);
                        cadastrarProdutos.setEnabled(true);
                        cadastrarProdutos.setVisibility(Switch.VISIBLE);
                    }
                });

                imageView = dialogView.findViewById(R.id.iv_dialog_usuario_imagem);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int PICK_IMAGE = 1234;
                        Intent i = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(i, "Selecione uma imagem"), PICK_IMAGE);
                    }
                });

                final EditText edtDialogTelefone = dialogView.findViewById(R.id.edt_dialog_usuario_telefone);
                SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
                MaskTextWatcher mtw = new MaskTextWatcher(edtDialogTelefone, smf);
                edtDialogTelefone.addTextChangedListener(mtw);

                builder.setView(dialogView).setTitle("Adicionar usuário")
                        .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Usuario usuario = new Usuario();

                                final EditText edtDialogNome = dialogView.findViewById(R.id.edt_dialog_usuario_nome);
                                final EditText edtDialogEmail = dialogView.findViewById(R.id.edt_dialog_usuario_email);
                                final EditText edtDialogLogin = dialogView.findViewById(R.id.edt_dialog_usuario_login);
                                final EditText edtDialogSenha = dialogView.findViewById(R.id.edt_dialog_usuario_senha);

                                String telefone = edtDialogTelefone.getText().toString()
                                        .replace("(","")
                                        .replace(")","")
                                        .replace("-","")
                                        .replace(" ","");


                                usuario.setNome(edtDialogNome.getText().toString());
                                usuario.setEmail(edtDialogEmail.getText().toString());
                                usuario.setTelefone(telefone);
                                usuario.setLogin(edtDialogLogin.getText().toString());
                                usuario.setSenha(edtDialogSenha.getText().toString());

                                //Verificar se os campos estão preenchidos
                                if (!usuario.getLogin().equals("")
                                        && !usuario.getSenha().equals("")
                                        && !usuario.getNome().equals("")
                                        && !usuario.getEmail().equals("")
                                        && !telefone.equals("")) {

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

                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                                    int idEmpresa = sharedPreferences.getInt("id_empresa",0);

                                    usuario.setEmp_codigo(id);

                                    usuario.setCadastrarProdutos(cadastrarProdutos.isChecked());
                                    usuario.setFotoImageView(imageView);

                                    usuario.setAtivo(true);
                                    try {

                                        int idUsuario = ((int) dao.salvar(usuario));

                                        if (idUsuario == 0) { //usuário já existe
                                            Toast.makeText(getActivity(),
                                                    "O Login informado está indisponível, tente novamente com um login diferente",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            usuario.setId(idUsuario);
                                            Toast.makeText(getActivity(),
                                                    "Usuário cadastrado com sucesso",
                                                    Toast.LENGTH_LONG).show();
                                            mAdapter.updateList(usuario);
                                        }


                                    } catch (Exception e) {
                                        Log.e("ProdutosFragment", e.getMessage());
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

    private void editClick(View v, int id, final int position) {

        final Usuario usuario = mAdapter.getItemById(id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_usuario, null);

        final EditText edtDialogNome = dialogView.findViewById(R.id.edt_dialog_usuario_nome);
        final EditText edtDialogEmail = dialogView.findViewById(R.id.edt_dialog_usuario_email);
        final EditText edtDialogLogin = dialogView.findViewById(R.id.edt_dialog_usuario_login);
        final EditText edtDialogSenha = dialogView.findViewById(R.id.edt_dialog_usuario_senha);
        final RadioGroup radioGroupDialog = dialogView.findViewById(R.id.radio_group_dialog_usuario);
        final Switch cadastrarProdutos = dialogView.findViewById(R.id.sw_cadastrar_produtos);

        RadioButton radioButtonAdm = dialogView.findViewById(R.id.radio_adm);
        RadioButton radioButtonUser = dialogView.findViewById(R.id.radio_user);

        radioButtonAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarProdutos.setChecked(true);
                cadastrarProdutos.setEnabled(false);
                cadastrarProdutos.setVisibility(Switch.INVISIBLE);
            }
        });

        radioButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarProdutos.setChecked(false);
                cadastrarProdutos.setEnabled(true);
                cadastrarProdutos.setVisibility(Switch.VISIBLE);
            }
        });

        imageView = dialogView.findViewById(R.id.iv_dialog_usuario_imagem);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int PICK_IMAGE = 1234;
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Selecione uma imagem"), PICK_IMAGE);
            }
        });

        final EditText edtDialogTelefone = dialogView.findViewById(R.id.edt_dialog_usuario_telefone);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edtDialogTelefone, smf);
        edtDialogTelefone.addTextChangedListener(mtw);

        if (usuario.getImagem_usuario() != null) {
            imageView.setImageBitmap(usuario.getImageView());
        }

        cadastrarProdutos.setChecked(usuario.isCadastrarProdutos());
        edtDialogTelefone.setText(usuario.getTelefone());
        edtDialogNome.setText(usuario.getNome());
        edtDialogEmail.setText(usuario.getEmail());
        edtDialogLogin.setText(usuario.getLogin());
        edtDialogSenha.setText(usuario.getSenha());

        if (usuario.isAdm()) {
            radioGroupDialog.check(R.id.radio_adm);
            cadastrarProdutos.setVisibility(Switch.INVISIBLE);
            cadastrarProdutos.setEnabled(false);
        } else {
            radioGroupDialog.check(R.id.radio_user);
            cadastrarProdutos.setVisibility(Switch.VISIBLE);
            cadastrarProdutos.setEnabled(true);
        }

        builder.setView(dialogView).setTitle("Editar usuário")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String telefone = edtDialogTelefone.getText().toString()
                                .replace("(","")
                                .replace(")","")
                                .replace("-","")
                                .replace(" ","");

                        usuario.setNome(edtDialogNome.getText().toString());
                        usuario.setEmail(edtDialogEmail.getText().toString());
                        usuario.setTelefone(telefone);
                        usuario.setLogin(edtDialogLogin.getText().toString());
                        usuario.setSenha(edtDialogSenha.getText().toString());

                        //Verificar se os campos estão preenchidos
                        if (!usuario.getLogin().equals("")
                                && !usuario.getSenha().equals("")
                                && !usuario.getNome().equals("")
                                && !usuario.getEmail().equals("")
                                && !telefone.equals("")) {

                            if (radioGroupDialog.getCheckedRadioButtonId() == R.id.radio_adm) {
                                usuario.setAdm(true);
                            } else if (radioGroupDialog.getCheckedRadioButtonId() == R.id.radio_user) {
                                usuario.setAdm(false);
                            } else {
                                Toast.makeText(getContext(),
                                        "Selecione um nível de acesso para o usuário",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            usuario.setCadastrarProdutos(cadastrarProdutos.isChecked());
                            usuario.setFotoImageView(imageView);

                            try {

                                dao.editar(usuario);

                            } catch (Exception e) {
                                Log.e("RecyclerUsuariosAdapter", e.getMessage());
                                Toast.makeText(getContext(),
                                        "Falha ao atualizar o usuário, tente novamente",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(getContext(),
                                    "Usuário atualizado com sucesso",
                                    Toast.LENGTH_SHORT).show();

                            mUsers.set(position, usuario);
                            mAdapter.notifyItemChanged(position);

                        } else { //Campos não preenchidos
                            Toast.makeText(getContext(),
                                    "Preencha todos os campos para salvar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    ArrayList<Usuario> mUsers;

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mUsers = new ArrayList<>(dao.recuperarAtivos());

        mAdapter = new RecyclerUsuariosAdapter(mUsers, getContext(), new CustomEditClickListener() {
            @Override
            public void onEditClick(View v, int id, int position) {
                editClick(v, id, position);
            }
        }, new CustomDeleteClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                final int p = position;
                final Usuario usuario = mUsers.get(position);

                final SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                final int idUsuarioLogado = sharedPreferences.getInt("user_id", 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Excluir usuário").setMessage("Tem certeza que deseja excluir o usuário " + mUsers.get(p).getLogin() + "?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    usuario.setAtivo(false);
                                    dao.editar(usuario);

                                } catch (Exception e) {
                                    Log.e("RecyclerUsuariosAdapter", e.getMessage());
                                    Toast.makeText(getContext(),
                                            "Falha ao excluir o usuário, tente novamente",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Toast.makeText(getContext(),
                                        "Usuário excluído com sucesso",
                                        Toast.LENGTH_SHORT).show();

                                mUsers.remove(p);
                                mAdapter.notifyItemRemoved(p);
                                mAdapter.notifyItemRangeChanged(p, mUsers.size());

                                if (usuario.getId() == idUsuarioLogado) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("user_id", 0);
                                    editor.commit();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }


                            }
                        })
                        .setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

}
