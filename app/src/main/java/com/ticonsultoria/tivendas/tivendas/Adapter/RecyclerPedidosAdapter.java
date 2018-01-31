package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.BD.ClienteDAO;
import com.ticonsultoria.tivendas.tivendas.BD.ItemDAO;
import com.ticonsultoria.tivendas.tivendas.BD.PedidoDAO;
import com.ticonsultoria.tivendas.tivendas.BD.ProdutoDAO;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.CadastrarPedidosFragment;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomDeleteClickListener;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomEditClickListener;
import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Cliente;
import com.ticonsultoria.tivendas.tivendas.model.Item;
import com.ticonsultoria.tivendas.tivendas.model.Pedido;
import com.ticonsultoria.tivendas.tivendas.model.Produto;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 26/01/2018.
 */

public class RecyclerPedidosAdapter extends RecyclerView.Adapter<RecyclerPedidosAdapter.RecyclerHolder> {
    private final List<Pedido> mPedidos;
    private final List<Cliente> mClientes;
    private final List<Usuario> mUsuarios;
    private final Context context;

    private PedidoDAO daoPedido;
    private UsuarioDAO daoUsuario;
    private ClienteDAO daoCliente;
    private ProdutoDAO daoProduto;
    private ItemDAO daoItem;

    CustomEditClickListener editListener;

    public RecyclerPedidosAdapter(ArrayList pedidos, Context c, CustomEditClickListener editListener) {
        mPedidos = pedidos;

        context = c;

        daoPedido = new PedidoDAO(c);
        daoUsuario = new UsuarioDAO(c);
        daoCliente = new ClienteDAO(c);
        daoProduto = new ProdutoDAO(c);
        daoItem = new ItemDAO(c);

        mUsuarios = daoUsuario.recuperarAtivos();
        mClientes = daoCliente.recuperarAtivos();

        this.editListener = editListener;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerPedidosAdapter.RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_pedidos, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {

        String nomeCliente = "";

        for (int i=0; i < mClientes.size(); i++) {
            if (mClientes.get(i).getId() == mPedidos.get(position).getId_cliente()) {
                nomeCliente = mClientes.get(i).getNome();
            }
        }

        holder.nomeCliente.setText(nomeCliente);
        holder.codigoPedido.setText(mPedidos.get(position).getNumero_pedido());

        holder.showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarItem(position);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editListener.onEditClick(view, mPedidos.get(position).getId());
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerItem(position);
            }
        });
    }


    //método responsável por exibir informações completas de um item
    private void mostrarItem(int position) {

        final Pedido pedido = mPedidos.get(position);

        String nomeCliente = "";
        String nomeUsuario = "";

        for (int i=0; i < mClientes.size(); i++) {
            if (mClientes.get(i).getId() == mPedidos.get(position).getId_cliente()) {
                nomeCliente = mClientes.get(i).getNome();
            }
        }
        for (int i=0; i < mUsuarios.size(); i++) {
            if (mUsuarios.get(i).getId() == mPedidos.get(position).getId_vendedor()) {
                nomeUsuario = mUsuarios.get(i).getLogin();
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_pedido, null);

        final EditText edtId = dialogView.findViewById(R.id.edt_dialog_pedido_codigo_pedido);
        final EditText edtNomeCliente = dialogView.findViewById(R.id.edt_dialog_pedido_nome_cliente);
        final EditText edtNomeVendedor = dialogView.findViewById(R.id.edt_dialog_pedido_nome_vendedor);
        final EditText edtData = dialogView.findViewById(R.id.edt_dialog_pedido_data);
        final EditText edtPreco = dialogView.findViewById(R.id.edt_dialog_pedido_preco);
        final EditText edtFormaPagamento = dialogView.findViewById(R.id.edt_dialog_pedido_forma_pagamento);

        edtId.setEnabled(false);
        edtPreco.setEnabled(false);
        edtNomeCliente.setEnabled(false);
        edtNomeVendedor.setEnabled(false);
        edtData.setEnabled(false);
        edtFormaPagamento.setEnabled(false);

        edtData.setTextColor(context.getResources().getColor(R.color.black));
        edtPreco.setTextColor(context.getResources().getColor(R.color.black));
        edtId.setTextColor(context.getResources().getColor(R.color.black));
        edtNomeCliente.setTextColor(context.getResources().getColor(R.color.black));
        edtNomeVendedor.setTextColor(context.getResources().getColor(R.color.black));
        edtFormaPagamento.setTextColor(context.getResources().getColor(R.color.black));

        edtId.setText("Código: " + pedido.getNumero_pedido());
        edtPreco.setText("Total: " + String.valueOf(pedido.getPrecoTotal()));
        edtData.setText("Data: " + pedido.getData());
        edtNomeCliente.setText("Cliente: " + nomeCliente);
        edtNomeVendedor.setText("Vendedor: " + nomeUsuario);
        edtFormaPagamento.setText("Forma de pagamento: " + pedido.getStringFormaPagamento());

        builder.setView(dialogView).setTitle("Detalhes");

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(final int position) {

        final Pedido pedido = mPedidos.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Excluir pedido").setMessage("Tem certeza que deseja excluir o pedido " + pedido.getNumero_pedido() + "?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                            pedido.setAtivo(false);
                            daoPedido.editar(pedido);

                            List<Item> itemList = daoItem.recuperarItensPedido(pedido.getId());

                            for (Item item: itemList) {
                                //devolver ao estoque
                                Produto produto = daoProduto.recuperarPorID(item.getId_produto());
                                produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
                                daoProduto.editar(produto);
                            }

                            Toast.makeText(context,
                                    "Pedido excluído com sucesso",
                                    Toast.LENGTH_SHORT).show();

                            mPedidos.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mPedidos.size());

                        } catch (Exception e) {
                            Log.e("RecyclerPedidosAdapter", e.getMessage());
                        }

                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return mPedidos != null ? mPedidos.size() : 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        public TextView nomeCliente;
        public TextView codigoPedido;
        public ImageButton showButton;
        public ImageButton editButton;
        public ImageButton deleteButton;

        public RecyclerHolder(View itemView) {
            super(itemView);
            nomeCliente = itemView.findViewById(R.id.txt_line_layout_pedidos_nome);
            codigoPedido = itemView.findViewById(R.id.txt_line_layout_pedidos_codigo);
            showButton = itemView.findViewById(R.id.btn_line_layout_show);
            editButton = itemView.findViewById(R.id.btn_line_layout_edit);
            deleteButton = itemView.findViewById(R.id.btn_line_layout_delete);
        }
    }
}