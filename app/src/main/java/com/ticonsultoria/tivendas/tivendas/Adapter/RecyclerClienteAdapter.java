package com.ticonsultoria.tivendas.tivendas.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.ticonsultoria.tivendas.tivendas.BD.ClienteDAO;
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
    private ClienteDAO daoCliente;

    public RecyclerClienteAdapter(ArrayList clientes, Context c) {
        mClientes = clientes;
        context = c;
        daoCliente = new ClienteDAO(c);
    }
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_clientes, parent, false));
    }

    public boolean verificarCpfValido(String cpf) {
        for (Cliente cliente: mClientes) {
            if (cliente.getCpf().equals(cpf)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder,final int position) {
        holder.title.setText(mClientes.get(position).getNome());
        holder.nivel.setText(mClientes.get(position).getNomeMercado());

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

    private void updateItem(final int position) {

        final Cliente cliente = mClientes.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_cliente, null);

        final EditText edtDialogNome = dialogView.findViewById(R.id.edt_dialog_cliente_nome);
        final EditText edtDialogMercado = dialogView.findViewById(R.id.edt_dialog_cliente_mercado);
        final EditText edtDialogCpf = dialogView.findViewById(R.id.edt_dialog_cliente_cpf);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(edtDialogCpf, smf);
        edtDialogCpf.addTextChangedListener(mtw);

        edtDialogNome.setText(cliente.getNome());
        edtDialogMercado.setText(cliente.getNomeMercado());
        edtDialogCpf.setText(cliente.getCpf());

        builder.setView(dialogView).setTitle("Editar cliente")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String cpf = edtDialogCpf.getText().toString().replace(".","").replace("-","");


                        if (    edtDialogNome.getText().toString().equals("") ||
                                edtDialogMercado.getText().toString().equals("")||
                                cpf.equals("")
                                ){
                            Toast.makeText(context,
                                    "Preencha todos os campos para editar o Cliente",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //Verificar se já existe cliente com o cpf
                        boolean cpfValido = verificarCpfValido(cpf);
                        if (!cpfValido) {
                            Toast.makeText(context,
                                    "Já existe um cliente cadastrado com esse CPF",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }


                        cliente.setNome(edtDialogNome.getText().toString());
                        cliente.setNomeMercado(edtDialogMercado.getText().toString());
                        cliente.setCpf(cpf);

                        daoCliente.editar(cliente);

                        Toast.makeText(context,
                                "Cliente atualizado com sucesso",
                                Toast.LENGTH_SHORT).show();

                        mClientes.set(position, cliente);
                        notifyItemChanged(position);

                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void updateList(Cliente cliente){ insertItem(cliente);}

    private void insertItem(Cliente cliente){
        mClientes.add(cliente);
        notifyItemInserted(getItemCount());
    }

    private void removerItem(final int position) {
        final Cliente cliente = mClientes.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Excluir cliente").setMessage("Tem certeza que deseja excluir o cliente " + mClientes.get(position).getNome() + "?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            cliente.setAtivo(false);
                            daoCliente.editar(cliente);
                        } catch (Exception e){
                            Log.e("RecyclerClienteAdapter", e.getMessage());
                        }


                        Toast.makeText(context,
                                "Cliente desativado com sucesso",
                                Toast.LENGTH_SHORT).show();

                        mClientes.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mClientes.size());

                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public int getItemCount() {return mClientes != null ? mClientes.size() : 0;}

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
