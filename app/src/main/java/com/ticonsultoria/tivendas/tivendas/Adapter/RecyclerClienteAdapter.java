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
import android.widget.TextView;
import android.widget.Toast;

import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Helder on 18/01/2018.
 */

public class RecyclerClienteAdapter extends RecyclerView.Adapter<RecyclerClienteAdapter.RecyclerHolder>{

    private final List<Cliente> mClientes;
    private Context context;

    public RecyclerClienteAdapter(ArrayList clientes, Context c) {
        mClientes = clientes;
        context = c;
    }
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_clientes, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerHolder holder,final int position) {

        holder.title.setText(mClientes.get(position).getNome());
        holder.title.setText(mClientes.get(position).getNomeMercado());

        holder.editButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem(position);
            }

        });
        holder.deleteButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                removerItem(position);
            }
        });
    }

    public int getItemCont(){
        return mClientes != null ? mClientes.size() : 0;
    }

    public void updateList(Cliente cliente){ insertItem(cliente);}

    private void insertItem(Cliente cliente){
        mClientes.add(cliente);
        notifyItemInserted(getItemCont());
    }

    private void updateItem(int position) {

        final int p = position;
        Cliente cliente = mClientes.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_cliente, null);

        final EditText edtDialogNome = dialogView.findViewById(R.id.edt_dialog_cliente_nome);
        final EditText edtDialogMercado = dialogView.findViewById(R.id.edt_dialog_cliente_mercado);

        edtDialogNome.setText(cliente.getNome());
        edtDialogMercado.setText(cliente.getNomeMercado());


        builder.setView(dialogView).setTitle("Editar cliente")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Cliente cliente = new Cliente();

                        cliente.setNome(edtDialogNome.getText().toString());
                        cliente.setNomeMercado(edtDialogMercado.getText().toString());

                        //Verificar se os campos estão preenchidos
                        if (!cliente.getNome().equals("") && !cliente.getNomeMercado().equals("")) {

                            mClientes.set(p, cliente);
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
    private void removerItem(int position) {

        final int p = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Excluir cliente").setMessage("Tem certeza que deseja excluir o cliente " + mClientes.get(p).getNome() + "?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mClientes.remove(p);
                        notifyItemRemoved(p);
                        notifyItemRangeChanged(p, mClientes.size());
                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class RecyclerHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView nivel;
        public ImageButton editButton;
        public ImageButton deleteButton;

        public RecyclerHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_line_layout_name_cliente);
            nivel = itemView.findViewById(R.id.txt_line_layout_nome_mercado);
            editButton = itemView.findViewById(R.id.btn_line_layout_edit_cliente);
            deleteButton = itemView.findViewById(R.id.btn_line_layout_delete_cliente);
        }
    }
}
