package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    public RecyclerUsuariosAdapter(ArrayList users, Context c) {
        mUsers = users;
        context = c;
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
    public void updateList(Usuario user) {
        insertItem(user);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(Usuario user) {
        mUsers.add(user);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por atualizar um usuário já existente na lista.
    private void updateItem(int position) {

        final int p = position;
        Usuario user = mUsers.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_usuario, null);

        final EditText edtDialogLogin = dialogView.findViewById(R.id.edt_dialog_usuario_login);
        final EditText edtDialogSenha = dialogView.findViewById(R.id.edt_dialog_usuario_senha);
        final RadioGroup radioGroupDialog = dialogView.findViewById(R.id.radio_group_dialog_usuario);

        edtDialogLogin.setText(user.getLogin());
        edtDialogSenha.setText(user.getSenha());

        if (user.isAdm()) {
            radioGroupDialog.check(R.id.radio_adm);
        } else {
            radioGroupDialog.check(R.id.radio_user);
        }

        builder.setView(dialogView).setTitle("Editar usuário")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Usuario usuario = new Usuario();

                        usuario.setLogin(edtDialogLogin.getText().toString());
                        usuario.setSenha(edtDialogSenha.getText().toString());

                        //Verificar se os campos estão preenchidos
                        if (!usuario.getLogin().equals("") && !usuario.getSenha().equals("")) {

                            if (radioGroupDialog.getCheckedRadioButtonId() == R.id.radio_adm) {
                                usuario.setAdm(true);
                            } else if (radioGroupDialog.getCheckedRadioButtonId() == R.id.radio_user) {
                                usuario.setAdm(false);
                            } else {
                                Toast.makeText(context,
                                        "Selecione um nível de acesso para o usuário",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            mUsers.set(p, usuario);
                            notifyItemChanged(p);

                        } else { //Campos não preenchidos
                            Toast.makeText(context,
                                    "Preencha todos os campos para salvar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(int position) {

        final int p = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Excluir usuário").setMessage("Tem certeza que deseja excluir o usuário " + mUsers.get(p).getLogin() + "?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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

        public RecyclerHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_line_layout_name);
            nivel = itemView.findViewById(R.id.txt_line_layout_nivel_acesso);
            editButton = itemView.findViewById(R.id.btn_line_layout_edit);
            deleteButton = itemView.findViewById(R.id.btn_line_layout_delete);
        }
    }

}

