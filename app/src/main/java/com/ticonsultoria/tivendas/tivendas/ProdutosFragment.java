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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerProdutosAdapter;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerUsuariosAdapter;
import com.ticonsultoria.tivendas.tivendas.BD.ProdutoDAO;
import com.ticonsultoria.tivendas.tivendas.model.Produto;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutosFragment extends Fragment {

    RecyclerView mRecyclerView;
    FloatingActionButton floatingActionButton;

    private RecyclerProdutosAdapter mAdapter;

    private ProdutoDAO dao;
    ImageView image;

    public ProdutosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_CANCELED){
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            int nh = (int) (bitmap.getHeight()* (512.0 / bitmap.getWidth()));
            Bitmap bitmapaux = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            image.setImageBitmap(bitmapaux);
            }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_produtos, container, false);

        mRecyclerView = view.findViewById(R.id.rv_produtos);
        floatingActionButton = view.findViewById(R.id.fab_produtos_add);

        dao = new ProdutoDAO(getContext());

        setupRecycler();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_produto, null);

                final ImageButton edtImagem = dialogView.findViewById(R.id.edt_dialog_imagem_produto);
                image = dialogView.findViewById(R.id.edt_dialog_image_view_produto);

                edtImagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int PICK_IMAGE = 1234;
                        Intent i = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(i, "Selecione uma imagem"), PICK_IMAGE);
                    }
                });


                builder.setView(dialogView).setTitle("Adicionar produto")
                        .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Produto produto = new Produto();

                                final EditText edtNome = dialogView.findViewById(R.id.edt_dialog_produto_nome_produto);
                                final EditText edtPreco = dialogView.findViewById(R.id.edt_dialog_produto_preco);
                                final EditText edtCategoria = dialogView.findViewById(R.id.edt_dialog_produto_categoria);
                                final EditText edtFornecedor = dialogView.findViewById(R.id.edt_dialog_produto_fornecedor);
                                final EditText edtMarca = dialogView.findViewById(R.id.edt_dialog_produto_marca);
                                final EditText edtQuantidade = dialogView.findViewById(R.id.edt_dialog_produto_quantidade);


                                //Verificar se os campos estão preenchidos
                                if (    edtNome.getText().toString().equals("") ||
                                        edtPreco.getText().toString().equals("") ||
                                        edtCategoria.getText().toString().equals("") ||
                                        edtFornecedor.getText().toString().equals("") ||
                                        edtMarca.getText().toString().equals("") ||
                                        edtQuantidade.getText().toString().equals("")
                                        ) {

                                    Toast.makeText(getActivity(),
                                            "Preencha todos os campos para adicionar um produto",
                                            Toast.LENGTH_SHORT).show();
                                    return;

                                }

                                produto.setNome_produto(edtNome.getText().toString());
                                produto.setPreco(Double.parseDouble(edtPreco.getText().toString()));
                                produto.setCategoria(edtCategoria.getText().toString());
                                produto.setFornecedor(edtFornecedor.getText().toString());
                                produto.setMarca(edtMarca.getText().toString());
                                produto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
                                produto.setFotoImageView(image);

                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                                int idEmpresa = sharedPreferences.getInt("id_empresa",0);

                                produto.setEmp_codigo(idEmpresa);

                                produto.setAtivo(true);

                                try {

                                    produto.setId((int) dao.salvar(produto));

                                    Toast.makeText(getActivity(),
                                            "Produto cadastrado com sucesso",
                                            Toast.LENGTH_SHORT).show();

                                    mAdapter.updateList(produto);

                                } catch (Exception e) {
                                    Log.e("ProdutosFragment", e.getMessage());
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

        ArrayList<Produto> array = new ArrayList<>(dao.recuperarAtivos());

        mAdapter = new RecyclerProdutosAdapter(array, getContext());
        mRecyclerView.setAdapter(mAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

}
