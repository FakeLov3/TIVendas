package com.ticonsultoria.tivendas.tivendas;

import android.test.AndroidTestCase;

import com.ticonsultoria.tivendas.tivendas.BD.ProdutoDAO;
import com.ticonsultoria.tivendas.tivendas.model.Produto;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProdutoDAOTest extends AndroidTestCase{
    @Test
    public void testSalvar() {
        ProdutoDAO produtoDAO = new ProdutoDAO(getContext());

        int primeiroSize = produtoDAO.recuperarTodos().size();

        Produto produto = criarProdutoExemplo();
        produtoDAO.salvar(produto);

        assertTrue(produtoDAO.recuperarTodos().size() > primeiroSize);
        produtoDAO.fecharConexao();
    }

    public Produto criarProdutoExemplo() {
        return new Produto(0, "Shampoo", 2.55 , "Higiene", "Ypê", "Ypê");
    }
}