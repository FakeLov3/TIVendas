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

import com.ticonsultoria.tivendas.tivendas.BD.ProdutoDAO;
import com.ticonsultoria.tivendas.tivendas.BD.UsuarioDAO;
import com.ticonsultoria.tivendas.tivendas.R;
import com.ticonsultoria.tivendas.tivendas.model.Produto;
import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpire on 23/01/2018.
 */

public class RecyclerProdutosAdapter extends RecyclerView.Adapter<RecyclerProdutosAdapter.RecyclerHolder> {

    private final List<Produto> mProdutos;
    private final Context context;
    private ProdutoDAO dao;

    public RecyclerProdutosAdapter(ArrayList users, Context c) {
        mProdutos = users;
        context = c;
        dao = new ProdutoDAO(c);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerProdutosAdapter.RecyclerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_produto, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {
        holder.nome.setText(mProdutos.get(position).getNome_produto());
        holder.preco.setText(String.valueOf(mProdutos.get(position).getPreco()));

        holder.showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

    // Método responsável por atualizar um usuário já existente na lista.
    private void updateItem(final int position) {

        final Produto produto = mProdutos.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_produto, null);

        final EditText edtNome = dialogView.findViewById(R.id.edt_dialog_produto_nome_produto);
        final EditText edtPreco = dialogView.findViewById(R.id.edt_dialog_produto_preco);
        final EditText edtCategoria = dialogView.findViewById(R.id.edt_dialog_produto_categoria);
        final EditText edtFornecedor = dialogView.findViewById(R.id.edt_dialog_produto_fornecedor);
        final EditText edtMarca = dialogView.findViewById(R.id.edt_dialog_produto_marca);
        final EditText edtQuantidade = dialogView.findViewById(R.id.edt_dialog_produto_quantidade);

        edtNome.setText(produto.getNome_produto());
        edtPreco.setText(String.valueOf(produto.getPreco()));
        edtCategoria.setText(produto.getCategoria());
        edtFornecedor.setText(produto.getFornecedor());
        edtMarca.setText(produto.getMarca());
        edtQuantidade.setText(String.valueOf(produto.getQuantidade()));

        builder.setView(dialogView).setTitle("Editar produto")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Verificar se os campos estão preenchidos
                        if (    edtNome.getText().toString().equals("") ||
                                edtPreco.getText().toString().equals("") ||
                                edtCategoria.getText().toString().equals("") ||
                                edtFornecedor.getText().toString().equals("") ||
                                edtMarca.getText().toString().equals("") ||
                                edtQuantidade.getText().toString().equals("")   ) {

                            Toast.makeText(context,
                                    "Preencha todos os campos para adicionar um produto",
                                    Toast.LENGTH_SHORT).show();
                            return;

                        }

                        produto.setNome_produto(edtNome.getText().toString());
                        produto.setPreco(Double.parseDouble(edtPreco.getText().toString()));
                        produto.setCategoria(edtCategoria.getText().toString());
                        produto.setFornecedor(edtFornecedor.getText().toString());
                        produto.setMarca(edtMarca.getText().toString());
                        produto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));

                        try {

                            dao.editar(produto);

                            Toast.makeText(context,
                                    "Produto atualizado com sucesso",
                                    Toast.LENGTH_SHORT).show();

                            mProdutos.set(position, produto);
                            notifyItemChanged(position);

                        } catch (Exception e) {
                            Log.e("RecyclerProdutosAdapter", e.getMessage());
                        }

                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void updateList(Produto produto) {
        insertItem(produto);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(Produto produto) {
        mProdutos.add(produto);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por remover um usuário da lista.
    private void removerItem(final int position) {

        final Produto produto = mProdutos.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Excluir produto").setMessage("Tem certeza que deseja excluir o produto " + mProdutos.get(position).getNome_produto() + "?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                            dao.deletar(produto);

                            Toast.makeText(context,
                                    "Produto excluído com sucesso",
                                    Toast.LENGTH_SHORT).show();

                            mProdutos.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mProdutos.size());

                        } catch (Exception e) {
                            Log.e("RecyclerProdutosAdapter", e.getMessage());
                        }

                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mProdutos != null ? mProdutos.size() : 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        public TextView nome;
        public TextView preco;
        public ImageButton showButton;
        public ImageButton editButton;
        public ImageButton deleteButton;

        public RecyclerHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txt_line_layout_nome_produto);
            preco = itemView.findViewById(R.id.txt_line_layout_preco_produtos);
            showButton = itemView.findViewById(R.id.btn_line_layout_show);
            editButton = itemView.findViewById(R.id.btn_line_layout_edit);
            deleteButton = itemView.findViewById(R.id.btn_line_layout_delete);
        }
    }
}
