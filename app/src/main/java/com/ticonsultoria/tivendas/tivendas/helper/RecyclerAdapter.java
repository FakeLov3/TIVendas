package com.ticonsultoria.tivendas.tivendas.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ticonsultoria.tivendas.tivendas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 17/01/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    private final List<String> mUsers;

    public RecyclerAdapter(ArrayList users) {
        mUsers = users;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_line_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {
        holder.title.setText(mUsers.get(position));

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem(position);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    /**
     * Método publico chamado para atualziar a lista.
     * @param user Novo objeto que será incluido na lista.
     */
    public void updateList(String user) {
        insertItem(user);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(String user) {
        mUsers.add(user);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por atualizar um usuário já existente na lista.
    private void updateItem(int position) {
        /*String user = mUsers.get(position);
        userModel.incrementAge();
        notifyItemChanged(position);*/
    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(int position) {
        mUsers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mUsers.size());
    }

}