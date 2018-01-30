package com.ticonsultoria.tivendas.tivendas;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerAdicionarProdutosAdapter;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerClienteAdapter;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerItensPedidoAdapter;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerPedidosAdapter;
import com.ticonsultoria.tivendas.tivendas.Adapter.RecyclerProcurarClienteAdapter;
import com.ticonsultoria.tivendas.tivendas.BD.ClienteDAO;
import com.ticonsultoria.tivendas.tivendas.BD.ItemDAO;
import com.ticonsultoria.tivendas.tivendas.BD.PedidoDAO;
import com.ticonsultoria.tivendas.tivendas.BD.ProdutoDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomDeleteClickListener;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomItemClickListener;
import com.ticonsultoria.tivendas.tivendas.model.Cliente;
import com.ticonsultoria.tivendas.tivendas.model.Item;
import com.ticonsultoria.tivendas.tivendas.model.Pedido;
import com.ticonsultoria.tivendas.tivendas.model.Produto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastrarPedidosFragment extends Fragment {

    public static final String INFO_TOTAL = "Total: R$ ";
    double total = 0;

    RecyclerView mRecyclerView;

    Cliente cliente;
    Pedido pedido;

    ArrayList<Produto> mProdutos;

    ItemDAO dao;
    PedidoDAO daoPedido;
    ClienteDAO daoCliente;
    ProdutoDAO daoProduto;

    RecyclerItensPedidoAdapter mAdapter;

    public CadastrarPedidosFragment() {
        // Required empty public constructor
    }

    TextView txtInfoTotal;

    EditText edtCliente;
    RadioGroup rgFormaPagamento;

    boolean editando;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastrar_pedidos, container, false);

        Bundle bundle = getArguments();

        mRecyclerView = view.findViewById(R.id.rv_itens_pedidos);

        daoPedido = new PedidoDAO(getContext());
        daoCliente = new ClienteDAO(getContext());


        pedido = new Pedido();

        daoProduto = new ProdutoDAO(getContext());
        mProdutos = new ArrayList<>(daoProduto.recuperarAtivos());

        Button btnCancelar = view.findViewById(R.id.btn_cadastrar_pedido_cancelar);
        Button btnSalvar = view.findViewById(R.id.btn_cadastrar_pedido_salvar);
        ImageButton btnProcurarCliente = view.findViewById(R.id.btn_cadastrar_pedido_buscar_cliente);
        ImageButton btnAdicionarProduto = view.findViewById(R.id.btn_cadastrar_pedidos_adicionar_produtos);
        rgFormaPagamento = view.findViewById(R.id.rg_forma_pagamento);
        rgFormaPagamento.check(R.id.rg_forma_pagamento_vista);

        txtInfoTotal = view.findViewById(R.id.txt_info_total);
        txtInfoTotal.setText(INFO_TOTAL + total);

        edtCliente = view.findViewById(R.id.edt_cadastrar_pedido_nome_cliente);

        dao = new ItemDAO(getContext());

        setupRecycler();

        if (bundle.getBoolean("editando") == true) {
            carregarPedido(bundle.getInt("pedido_id"));
            editando = true;
        } else {
            editando = false;
        }

        //Botão procurar cliente
        btnProcurarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_cadastrar_pedido_procurar_cliente, null);

                final EditText edtBuscarCliente = dialogView.findViewById(R.id.txt_dialog_procurar_cliente_nome);
                ImageButton btnBuscar = dialogView.findViewById(R.id.btn_dialog_procurar_cliente_buscar);

                builder.setView(dialogView).setTitle("Clientes");

                final AlertDialog dialog = builder.create();

                RecyclerView recycler = dialogView.findViewById(R.id.rv_procurar_cliente);

                // Configurando o gerenciador de layout para ser uma lista.
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(layoutManager);

                //ArrayList<Cliente> array = new ArrayList<>(daoCliente.recuperarAtivos());
                final ArrayList<Cliente> array = new ArrayList<>(daoCliente.recuperarAtivos());

                // Adiciona o adapter que irá anexar os objetos à lista.
                // Está sendo criado com lista vazia, pois será preenchida posteriormente.
                final RecyclerProcurarClienteAdapter adapter = new RecyclerProcurarClienteAdapter(array, getContext(), new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        cliente = array.get(position);
                        edtCliente.setText(cliente.getNome());
                        dialog.dismiss();
                    }
                });
                recycler.setAdapter(adapter);
                // Configurando um dividr entre linhas, para uma melhor visualização.
                recycler.addItemDecoration(
                        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

                dialog.show();

                btnBuscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nome = edtBuscarCliente.getText().toString();
                        List<Cliente> lista;
                        if (nome.equals("")) {
                            lista = daoCliente.recuperarAtivos();
                        } else {
                            String query = "SELECT * FROM "
                                    + ClienteDAO.NOME_TABELA
                                    + " WHERE "
                                    + ClienteDAO.COLUNA_NOME_CLIENTE
                                    + " = '"
                                    + nome
                                    + "' AND "
                                    + ClienteDAO.COLUNA_ATIVO
                                    + " = 1";
                            lista = daoCliente.recuperarPorQuery(query);
                        }
                        adapter.removerTodos();
                        for (int i = 0; i < lista.size(); i++) {
                            adapter.insertItem(lista.get(i));
                        }

                    }
                });

            }
        });

        //Botão adicionar produto
        btnAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_cadastrar_pedido_procurar_cliente, null);

                final EditText edtBuscarProduto = dialogView.findViewById(R.id.txt_dialog_procurar_cliente_nome);
                edtBuscarProduto.setHint("Nome do produto");
                ImageButton btnBuscar = dialogView.findViewById(R.id.btn_dialog_procurar_cliente_buscar);

                builder.setView(dialogView).setTitle("Produtos");

                final AlertDialog dialog = builder.create();

                RecyclerView recycler = dialogView.findViewById(R.id.rv_procurar_cliente);

                // Configurando o gerenciador de layout para ser uma lista.
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(layoutManager);

                // Adiciona o adapter que irá anexar os objetos à lista.
                // Está sendo criado com lista vazia, pois será preenchida posteriormente.
                final RecyclerAdicionarProdutosAdapter adapter = new RecyclerAdicionarProdutosAdapter(mProdutos, getContext(), new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_selecionar_quantidade_produtos, null);

                        TextView txtNomeProduto = dialogView.findViewById(R.id.txt_nome);
                        TextView txtEstoque = dialogView.findViewById(R.id.txt_estoque);
                        TextView txtPreco = dialogView.findViewById(R.id.txt_preco);
                        txtNomeProduto.setText("Produto: " + mProdutos.get(position).getNome_produto());
                        txtEstoque.setText("Estoque: " + mProdutos.get(position).getQuantidade());
                        txtPreco.setText("Preço unitário: " + mProdutos.get(position).getPreco());

                        final EditText edtQuantidade = dialogView.findViewById(R.id.edt_quantidade);

                        builder.setView(dialogView).setTitle("Quantidade").setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                int quant = Integer.parseInt(edtQuantidade.getText().toString());

                                if (quant <= 0) {
                                    Toast.makeText(getContext(), "Quantidade inválida", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                if (quant > mProdutos.get(position).getQuantidade()) {
                                    Toast.makeText(getContext(), "Quantidade superior ao estoque", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                mProdutos.get(position).setQuantidade(mProdutos.get(position).getQuantidade() - quant);

                                Item item = new Item();
                                item.setQuantidade(quant);
                                item.setId_produto(mProdutos.get(position).getId());
                                mAdapter.insertItem(item);
                                total += (mProdutos.get(position).getPreco() * quant);
                                txtInfoTotal.setText(String.format(INFO_TOTAL + "%.2f", total));

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        final AlertDialog dialog = builder.create();

                        dialog.show();

                    }
                });
                recycler.setAdapter(adapter);
                // Configurando um dividr entre linhas, para uma melhor visualização.
                recycler.addItemDecoration(
                        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

                dialog.show();

                btnBuscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nome = edtBuscarProduto.getText().toString();
                        List<Produto> lista;
                        if (nome.equals("")) {
                            lista = daoProduto.recuperarAtivos();
                        } else {
                            String query = "SELECT * FROM "
                                    + ProdutoDAO.NOME_TABELA
                                    + " WHERE "
                                    + ProdutoDAO.COLUNA_NOME_PRODUTO
                                    + " = '"
                                    + nome
                                    + "' AND "
                                    + ProdutoDAO.COLUNA_ATIVO
                                    + " = 1";
                            lista = daoProduto.recuperarPorQuery(query);
                        }
                        adapter.removerTodos();
                        for (int i = 0; i < lista.size(); i++) {
                            adapter.insertItem(lista.get(i));
                        }

                    }
                });
            }
        });


        //Botão Salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pedido pedido = getPedido();

                if (cliente == null) {
                    Toast.makeText(getContext(), "Selecione um cliente", Toast.LENGTH_LONG).show();
                    return;
                }

                pedido.setId_cliente(cliente.getId());

                if (rgFormaPagamento.getCheckedRadioButtonId() == R.id.rg_forma_pagamento_vista) {
                    pedido.setFormaPagamento(Pedido.PAGAMENTO_VISTA);
                } else if (rgFormaPagamento.getCheckedRadioButtonId() == R.id.rg_forma_pagamento_prazo) {
                    pedido.setFormaPagamento(Pedido.PAGAMENTO_PRAZO);
                } else {
                    Toast.makeText(getContext(), "Forma de pagamento inválida", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mAdapter.getItemCount() <= 0) {
                    Toast.makeText(getContext(), "Selecione algum produto para continuar", Toast.LENGTH_SHORT).show();
                    return;
                }

                pedido.setPrecoTotal(total);

                if (editando) {

                    daoPedido.editar(pedido);
                    for (Item item: mListaItensAntiga) {
                        //deletar itens antigos e devolver ao estoque
                        Produto produto = daoProduto.recuperarPorID(item.getId_produto());
                        produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
                        daoProduto.editar(produto);
                        dao.deletar(item);
                    }

                    //salvar novos itens e subtrair do estoque
                    for (Item item: mAdapter.getmItens()) {
                        item.setId_pedido(pedido.getId());
                        dao.salvar(item);
                        Produto produto = daoProduto.recuperarPorID(item.getId_produto());
                        produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
                        daoProduto.editar(produto);
                    }

                    retornar();
                    return;
                }

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("user_id",0);

                if (userId != 0) {
                    pedido.setId_vendedor(userId);
                } else {
                    Toast.makeText(getContext(), "Vendedor inválido, faça login novamente", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Pedido> pedidos = daoPedido.recuperarPorQuery(
                        "SELECT * FROM " + PedidoDAO.NOME_TABELA + " WHERE " + PedidoDAO.COLUNA_CODIGO_PEDIDO
                        + " = (SELECT MAX(" + PedidoDAO.COLUNA_CODIGO_PEDIDO + ") FROM " + PedidoDAO.NOME_TABELA + ")"
                );

                if (pedidos.size() > 0) {
                    pedido.setCodigo_pedido(pedidos.get(0).getCodigo_pedido() + 1);
                } else {
                    pedido.setCodigo_pedido(1);
                }

                pedido.setNumero_pedido(pedido.getCodigo_pedido() + "/" + pedido.getId_vendedor());

                Date data = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                Date data_atual = cal.getTime();

                pedido.setData(data_atual);

                pedido.setAtivo(true);

                pedido.setId((int) daoPedido.salvar(pedido));

                List<Item> itens = mAdapter.getmItens();

                for (Item item: itens) {
                    item.setId_pedido(pedido.getId());
                    dao.salvar(item);
                    for (Produto p: mProdutos) {
                        if (p.getId() == item.getId_produto()) {
                            daoProduto.editar(p);
                        }
                    }
                }

                retornar();

            }
        });


        //Botão Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retornar();
            }
        });

        return view;
    }

    public Pedido getPedido() {
        return pedido;
    }

    List<Item> mListaItensAntiga;

    private void carregarPedido(int pedido_id) {
        pedido = daoPedido.recuperarPorID(pedido_id);
        List<Produto> produtos = daoProduto.recuperarTodos();

        mListaItensAntiga = dao.recuperarItensPedido(pedido.getId());

        mAdapter.removerTodos();
        for (Item item: mListaItensAntiga) {
            mAdapter.insertItem(item);
        }

        total = pedido.getPrecoTotal();

        txtInfoTotal.setText(String.format(INFO_TOTAL + "%.2f", total));

        if (pedido.getFormaPagamento().equals(Pedido.PAGAMENTO_PRAZO)) {
            rgFormaPagamento.check(R.id.rg_forma_pagamento_prazo);
        } else if (pedido.getFormaPagamento().equals(Pedido.PAGAMENTO_VISTA)) {
            rgFormaPagamento.check(R.id.rg_forma_pagamento_vista);
        } else {
            Toast.makeText(getContext(), "Erro: forma de pagamento inválida", Toast.LENGTH_SHORT).show();
        }

        cliente = daoCliente.recuperarPorID(pedido.getId_cliente());
        edtCliente.setText(cliente.getNome());

    }

    private void retornar() {
        android.support.v4.app.Fragment fragment = new PedidosFragment();

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //ArrayList<Cliente> array = new ArrayList<>(daoCliente.recuperarAtivos());
        ArrayList<Item> array = new ArrayList<>();

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new RecyclerItensPedidoAdapter(array, getContext(), new CustomDeleteClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                Item item = mAdapter.getItem(position);

                double preco = 0;

                //devolver a quantidade ao estoque
                for (int i = 0; i < mProdutos.size(); i++) {
                    if (mProdutos.get(i).getId() == item.getId_produto()) {
                        mProdutos.get(i).setQuantidade(mProdutos.get(i).getQuantidade() + item.getQuantidade());
                        preco =  mProdutos.get(i).getPreco();
                    }
                }

                total -= item.getQuantidade() * preco;
                txtInfoTotal.setText(String.format(INFO_TOTAL + "%.2f", total));

                mAdapter.removerItem(position);

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

}