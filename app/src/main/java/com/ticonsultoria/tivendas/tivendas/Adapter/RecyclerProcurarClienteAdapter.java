package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ticonsultoria.tivendas.tivendas.Helper.CustomItemClickListener;
import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 28/01/2018.
 */

public class RecyclerProcurarClienteAdapter extends RecyclerView.Adapter<RecyclerProcurarClienteAdapter.RecyclerHolder> {

    private final List<Cliente> mClientes;
    private final Context context;

    CustomItemClickListener listener;

    public RecyclerProcurarClienteAdapter(ArrayList itens, Context c, CustomItemClickListener listener) {
        mClientes = itens;
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

        holder.nomeCliente.setText(mClientes.get(position).getNome_cliente());
        holder.nomeMercado.setText(mClientes.get(position).getNomeMercado());

    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    public void insertItem(Cliente cliente) {
        mClientes.add(cliente);
        notifyItemInserted(getItemCount());
    }

    public void removerTodos() {
        int aux = mClientes.size();
        for (int i = 0; i < aux; i++) {
            mClientes.remove(0);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, mClientes.size());
        }
    }

    @Override
    public int getItemCount() {
        return mClientes != null ? mClientes.size() : 0;
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder {

        public TextView nomeCliente;
        public TextView nomeMercado;

        public RecyclerHolder(View itemView) {
            super(itemView);
            nomeCliente = itemView.findViewById(R.id.txt_line_layout_title);
            nomeMercado = itemView.findViewById(R.id.txt_line_layout_subtitle);
        }
    }

}