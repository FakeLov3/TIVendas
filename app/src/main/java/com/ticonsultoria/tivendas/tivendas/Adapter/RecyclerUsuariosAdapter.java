package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.Helper.CustomEditClickListener;
import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 17/01/2018.
 */

public class RecyclerUsuariosAdapter extends RecyclerView.Adapter<RecyclerUsuariosAdapter.RecyclerHolder> {

    private final List<Usuario> mUsers;
    private Context context;
    UsuarioDAO dao;

    CustomEditClickListener editClickListener;


    public RecyclerUsuariosAdapter(ArrayList users, Context c, CustomEditClickListener editClickListener) {
        mUsers = users;
        context = c;
        dao = new UsuarioDAO(context);

        this.editClickListener = editClickListener;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_usuario_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {
        holder.title.setText(mUsers.get(position).getLogin());
        holder.nivel.setText(mUsers.get(position).getStringAdm());

        if(mUsers.get(position).getImagem_usuario() != null) {
            holder.foto.setImageBitmap(mUsers.get(position).getImageView());
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClickListener.onEditClick(view, mUsers.get(position).getId(), position);
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
    public void updateList(Usuario user) {
        insertItem(user);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(Usuario user) {
        mUsers.add(user);
        notifyItemInserted(getItemCount());
    }

    public Usuario getItemById(int id) {

        for (Usuario usuario: mUsers) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(int position) {

        final int p = position;
        final Usuario usuario = mUsers.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Excluir usuário").setMessage("Tem certeza que deseja excluir o usuário " + mUsers.get(p).getLogin() + "?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                            usuario.setAtivo(false);
                            dao.editar(usuario);

                        } catch (Exception e) {
                            Log.e("RecyclerUsuariosAdapter", e.getMessage());
                            Toast.makeText(context,
                                    "Falha ao excluir o usuário, tente novamente",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(context,
                                "Usuário excluído com sucesso",
                                Toast.LENGTH_SHORT).show();

                        mUsers.remove(p);
                        notifyItemRemoved(p);
                        notifyItemRangeChanged(p, mUsers.size());

                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    class RecyclerHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView nivel;
        public ImageButton editButton;
        public ImageButton deleteButton;
        public ImageView foto;

        public RecyclerHolder(View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.iv_line_layout_foto);
            title = itemView.findViewById(R.id.txt_line_layout_name);
            nivel = itemView.findViewById(R.id.txt_line_layout_nivel_acesso);
            editButton = itemView.findViewById(R.id.btn_line_layout_edit);
            deleteButton = itemView.findViewById(R.id.btn_line_layout_delete);
        }
    }

}

