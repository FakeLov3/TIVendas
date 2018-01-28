package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ticonsultoria.tivendas.tivendas.Helper.CustomItemClickListener;
import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Produto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 28/01/2018.
 */

public class RecyclerAdicionarProdutosAdapter extends RecyclerView.Adapter<RecyclerAdicionarProdutosAdapter.RecyclerHolder> {

    private final List<Produto> mProdutos;
    private final Context context;

    CustomItemClickListener listener;

    public RecyclerAdicionarProdutosAdapter(ArrayList itens, Context c, CustomItemClickListener listener) {
        mProdutos = itens;
        context = c;
        this.listener = listener;
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_procurar_cliente, parent, false);
        final RecyclerHolder mViewHolder = new RecyclerHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        holder.nomeProduto.setText(mProdutos.get(position).getNome_produto());
        holder.marca.setText(mProdutos.get(position).getMarca());

    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    public void insertItem(Produto produto) {
        mProdutos.add(produto);
        notifyItemInserted(getItemCount());
    }

    public void removerTodos() {
        int aux = mProdutos.size();
        for (int i = 0; i < aux; i++) {
            mProdutos.remove(0);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, mProdutos.size());
        }
    }

    @Override
    public int getItemCount() {
        return mProdutos != null ? mProdutos.size() : 0;
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder {

        public TextView nomeProduto;
        public TextView marca;

        public RecyclerHolder(View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.txt_line_layout_title);
            marca = itemView.findViewById(R.id.txt_line_layout_subtitle);
        }
    }

}