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

import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerClienteAdapter;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerUsuariosAdapter;
import com.ticonsultoria.tivendas.tivendas.model.Cliente;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {

    RecyclerView mRecyclerViewC;
    FloatingActionButton floatingActionButtonC;

    private RecyclerClienteAdapter mAdapter;

    public ClientesFragment() {
    //precisa de um construtor limpo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        mRecyclerViewC = view.findViewById(R.id.rv_clientes);
        floatingActionButtonC = view.findViewById(R.id.fab_clientes_add);
        setupRecycler();

        //Acão do botão flutuante de adicionar
        floatingActionButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_cliente, null);

                builder.setView(dialogView).setTitle("Adicionar Cliente")
                        .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Cliente cliente = new Cliente();

                                final EditText edtDialogNome = dialogView.findViewById(R.id.edt_dialog_cliente_nome);
                                final EditText edtDialogMercado = dialogView.findViewById(R.id.edt_dialog_cliente_mercado);

                                cliente.setNome(edtDialogNome.getText().toString());
                                cliente.setNomeMercado(edtDialogMercado.getText().toString());

                                //Verificar se os campos estão preenchidos
                                if (!cliente.getNome().equals("") && !cliente.getNomeMercado().equals("")) {

                                    mAdapter.updateList(cliente);

                                } else { //Campos não preenchidos
                                    Toast.makeText(getActivity(),
                                            "Preencha todos os campos para adicionar um cliente",
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
        mRecyclerViewC.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new RecyclerClienteAdapter(new ArrayList<>(), getContext());
        mRecyclerViewC.setAdapter(mAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerViewC.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
    }


