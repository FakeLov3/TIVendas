package com.ticonsultoria.tivendas.tivendas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerClienteAdapter;
import com.ticonsultoria.tivendas.tivendas.BD.ClienteDAO;
import com.ticonsultoria.tivendas.tivendas.model.Cliente;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {

    RecyclerView mRecyclerViewC;
    FloatingActionButton floatingActionButtonC;

    private RecyclerClienteAdapter mAdapter;

    private ClienteDAO daoCliente;

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

        daoCliente = new ClienteDAO(getContext());

        setupRecycler();

        //Acão do botão flutuante de adicionar
        floatingActionButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_cliente, null);

                final EditText edtDialogCPF = dialogView.findViewById(R.id.edt_dialog_cliente_cpf);
                SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
                MaskTextWatcher mtw = new MaskTextWatcher(edtDialogCPF, smf);
                edtDialogCPF.addTextChangedListener(mtw);

                final EditText edtDialogTelefone = dialogView.findViewById(R.id.edt_dialog_cliente_telefone);
                SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
                MaskTextWatcher mtwTelefone = new MaskTextWatcher(edtDialogTelefone, smfTelefone);
                edtDialogTelefone.addTextChangedListener(mtwTelefone);

                builder.setView(dialogView).setTitle("Adicionar Cliente")
                        .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Cliente cliente = new Cliente();

                                final EditText edtDialogNome = dialogView.findViewById(R.id.edt_dialog_cliente_nome);
                                final EditText edtDialogMercado = dialogView.findViewById(R.id.edt_dialog_cliente_mercado);
                                final EditText edtDialogEmail = dialogView.findViewById(R.id.edt_dialog_cliente_email);

                                String cpf = edtDialogCPF.getText().toString().replace(".","").replace("-","");
                                String telefone = edtDialogTelefone.getText().toString().replace("(","").replace(")","").replace(" ","")
                                        .replace("-","");

                                //Verificar se os campos estão preenchidos
                                if (    edtDialogNome.getText().toString().equals("") ||
                                        edtDialogMercado.getText().toString().equals("") ||
                                        cpf.equals("") || edtDialogEmail.equals("") || telefone.equals("")){
                                    Toast.makeText(getActivity(),
                                            "Preencha todos os campos para adicionar um Cliente",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                //Verificar se já existe cliente com o cpf
                                boolean cpfValido = mAdapter.verificarCpfValido(cpf);
                                if (!cpfValido) {
                                    Toast.makeText(getActivity(),
                                            "Já existe um cliente cadastrado com esse CPF",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                                int emp_codido = sharedPreferences.getInt("id_empresa",0);
                                cliente.setNome_cliente(edtDialogNome.getText().toString());
                                cliente.setNomeMercado(edtDialogMercado.getText().toString());
                                cliente.setCpf(cpf);
                                cliente.setTelefone(telefone);
                                cliente.setEmail(edtDialogEmail.getText().toString());
                                cliente.setAtivo("S");
                                cliente.setEmp_codigo(emp_codido);


                                try {
                                    cliente.setId((int)daoCliente.salvar(cliente));

                                    Toast.makeText(getActivity(),
                                            "Cliente cadastrado com sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                    mAdapter.updateList(cliente);

                                } catch (Exception e){
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
        mRecyclerViewC.setLayoutManager(layoutManager);

        //ArrayList<Cliente> array = new ArrayList<>(daoCliente.recuperarAtivos());
        ArrayList<Cliente> array = new ArrayList<>(daoCliente.recuperarAtivos());

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new RecyclerClienteAdapter(array, getContext());
        mRecyclerViewC.setAdapter(mAdapter);
        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerViewC.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}


