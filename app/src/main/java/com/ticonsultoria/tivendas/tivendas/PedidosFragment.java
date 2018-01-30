package com.ticonsultoria.tivendas.tivendas;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerPedidosAdapter;
import com.ticonsultoria.tivendas.tivendas.BD.PedidoDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomEditClickListener;
import com.ticonsultoria.tivendas.tivendas.model.Pedido;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFragment extends Fragment {


    RecyclerView mRecyclerView;
    FloatingActionButton floatingActionButton;

    PedidoDAO dao;

    RecyclerPedidosAdapter mAdapter;

    public PedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        mRecyclerView = view.findViewById(R.id.rv_pedidos);
        floatingActionButton = view.findViewById(R.id.fab_pedidos_add);

        dao = new PedidoDAO(getContext());

        setupRecycler();

        //Acão do botão flutuante de adicionar
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment fragment = new CadastrarPedidosFragment();

                Bundle bundle = new Bundle();

                bundle.putBoolean("editando",false);

                fragment.setArguments(bundle);

                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        return view;
    }

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //ArrayList<Cliente> array = new ArrayList<>(daoCliente.recuperarAtivos());
        ArrayList<Pedido> array = new ArrayList<>(dao.recuperarAtivos());

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new RecyclerPedidosAdapter(array, getContext(), new CustomEditClickListener() {
            @Override
            public void onEditClick(View v, int id) {
                editarPedido(id);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void editarPedido(int id) {
        android.support.v4.app.Fragment fragment = new CadastrarPedidosFragment();

        Bundle bundle = new Bundle();

        bundle.putBoolean("editando", true);
        bundle.putInt("pedido_id", id);

        fragment.setArguments(bundle);

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

}
