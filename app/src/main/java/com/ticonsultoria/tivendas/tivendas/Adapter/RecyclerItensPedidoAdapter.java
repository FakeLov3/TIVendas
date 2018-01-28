package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ticonsultoria.tivendas.tivendas.BD.ItemDAO;
import com.ticonsultoria.tivendas.tivendas.BD.ProdutoDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomDeleteClickListener;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomItemClickListener;
import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Item;
import com.ticonsultoria.tivendas.tivendas.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 27/01/2018.
 */

public class RecyclerItensPedidoAdapter extends RecyclerView.Adapter<RecyclerItensPedidoAdapter.RecyclerHolder> {

    private final List<Item> mItens;
    private final Context context;
    private ItemDAO daoItem;

    private ProdutoDAO daoProduto;
    private List<Produto> mProdutos;

    CustomDeleteClickListener listener;

    public RecyclerItensPedidoAdapter(ArrayList itens, Context c, CustomDeleteClickListener listener) {
        mItens = itens;
        context = c;
        this.listener = listener;
        daoProduto = new ProdutoDAO(context);
        mProdutos = daoProduto.recuperarAtivos();
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerItensPedidoAdapter.RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_pedidos, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {

        Produto produto = null;

        for (int i = 0; i < mProdutos.size(); i++) {
            if (mProdutos.get(i).getId() == mItens.get(position).getId_produto()) {
                produto = mProdutos.get(i);
            }
        }

        holder.nomeItem.setText(produto.getNome_produto() + " - " + produto.getMarca());
        holder.codigoItem.setText(String.valueOf(mItens.get(position).getQuantidade()));

        holder.showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrarItem(position);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateItem(position);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(view, position);
            }
        });
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    public void insertItem(Item item) {
        mItens.add(item);
        notifyItemInserted(getItemCount());
    }

    public void removerTodos() {
        int aux = mItens.size();
        for (int i = 0; i < aux; i++) {
            mItens.remove(0);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, mItens.size());
        }
    }

    public void removerItem(int position) {
        mItens.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItens.size());
    }

    public Item getItem(int position) {
        return mItens.get(position);
    }

    public List<Item> getmItens() {
        return mItens;
    }

    @Override
    public int getItemCount() {
        return mItens != null ? mItens.size() : 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        public TextView nomeItem;
        public TextView codigoItem;
        public ImageButton showButton;
        public ImageButton editButton;
        public ImageButton deleteButton;

        public RecyclerHolder(View itemView) {
            super(itemView);
            nomeItem = itemView.findViewById(R.id.txt_line_layout_pedidos_nome);
            codigoItem = itemView.findViewById(R.id.txt_line_layout_pedidos_codigo);
            showButton = itemView.findViewById(R.id.btn_line_layout_show);
            editButton = itemView.findViewById(R.id.btn_line_layout_edit);
            deleteButton = itemView.findViewById(R.id.btn_line_layout_delete);
        }
    }

}
