package com.api.apiBookStore.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.api.apiBookStore.DTO.ItemSacola;
import com.api.apiBookStore.dao.BookStoreDAO;
import com.api.apiBookStore.model.Livros;

public class PaymentService {

    public Long registrarVenda(Long clienteId, List<ItemSacola> sacola, String tipoPagamento, String statusPagamento,
            Double valorTotal) throws SQLException {
        final String jbcURL = "jdbc:mysql://mydigitalbank.c7sog0s4qdes.us-east-2.rds.amazonaws.com:3306/BookStoreDB";
        final String user = "adminDigBank";
        final String pass = "Dce81125";

        Connection conn = DriverManager.getConnection(jbcURL, user, pass);
        conn.setAutoCommit(false);

        Long vendaId = null;

        try {
            // 1. Inserir a venda
            String insertVenda = "INSERT INTO vendas (cliente_id, valor_total, tipo_pagamento, status_pagamento) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtVenda = conn.prepareStatement(insertVenda, Statement.RETURN_GENERATED_KEYS);
            stmtVenda.setLong(1, clienteId);
            stmtVenda.setDouble(2, valorTotal);
            stmtVenda.setString(3, tipoPagamento);
            stmtVenda.setString(4, statusPagamento);
            stmtVenda.executeUpdate();

            ResultSet rs = stmtVenda.getGeneratedKeys();
            if (rs.next()) {
                vendaId = rs.getLong(1);
            }

            // 2. Inserir os itens da venda
            String insertItem = "INSERT INTO itens_venda (venda_id, livro_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtItem = conn.prepareStatement(insertItem);

            BookStoreDAO bookStoreDAO = new BookStoreDAO();

            for (ItemSacola item : sacola) {
                Livros livro = bookStoreDAO.buscarLivroPorId(item.getlivroId());
                stmtItem.setLong(1, vendaId);
                stmtItem.setLong(2, item.getlivroId());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, livro.getPreco());
                stmtItem.addBatch();
            }

            stmtItem.executeBatch();

            // 3. Inserir o pagamento
            String insertPagamento = "INSERT INTO pagamentos (venda_id, tipo, status, valor, mensagem) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtPagamento = conn.prepareStatement(insertPagamento);
            stmtPagamento.setLong(1, vendaId);
            stmtPagamento.setString(2, tipoPagamento);
            stmtPagamento.setString(3, statusPagamento);
            stmtPagamento.setDouble(4, valorTotal);
            stmtPagamento.setString(5, "Pagamento com " + tipoPagamento + " " + statusPagamento);
            stmtPagamento.executeUpdate();

            conn.commit();
            return vendaId;

        } catch (SQLException e) {
            conn.rollback();
            throw e;

        } finally {
            conn.close();
        }
    }
}
