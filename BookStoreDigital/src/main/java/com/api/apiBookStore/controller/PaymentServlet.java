package com.api.apiBookStore.controller;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api.apiBookStore.DTO.ItemSacola;
import com.api.apiBookStore.model.Pagamento;
import com.api.apiBookStore.util.PagamentoRequest;
import com.google.gson.Gson;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pagamento")
public class PaymentServlet extends HttpServlet {
    private final String jdbcURL = "jdbc:mysql://mydigitalbank.c7sog0s4qdes.us-east-2.rds.amazonaws.com:3306/BookStoreDB";
    private final String user = "adminDigBank";
    private final String pass = "Dce81125";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, java.io.IOException {
        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        PagamentoRequest pagamentoRequest = gson.fromJson(reader, PagamentoRequest.class);
        Pagamento pagamento = new Pagamento();

        double valorTotal = 0;
        StringBuilder titulos = new StringBuilder();

        try(Connection conn = DriverManager.getConnection(jdbcURL, user, pass)){
            for (ItemSacola item : pagamentoRequest.getSacola()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT preco, quant, titulo FROM livros WHERE id = ?");
                stmt.setLong(1, item.getlivroId());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    double preco = rs.getDouble("preco");
                    int quant = rs.getInt("quant");
                    String titulo = rs.getString("titulo");
                    
                    pagamento.setTitulo(titulo);

                    if (quant <= 0 || item.getQuantidade() > quant) {
                        pagamento.setStatus("erro");
                        pagamento.setMensagem("Estoque insuficiente para o livro ID: " + item.getlivroId());
                        enviarResposta(response, pagamento);
                        return;
                    }
                        valorTotal += preco * item.getQuantidade();

                        try (PreparedStatement updateStmt = conn
                                .prepareStatement("UPDATE livros SET quant = ? WHERE id = ?")) {
                            updateStmt.setInt(1, quant - item.getQuantidade());
                            updateStmt.setLong(2, item.getlivroId());
                            updateStmt.executeUpdate();
                        }

                        titulos.append(titulo).append(", ");
                    
                    
                    
                  
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    pagamento.setMensagem("Livro não encontrado com ID: " + item.getlivroId());
                    response.getWriter().write(gson.toJson(pagamento));
                    return;
                }
            }
        } catch (SQLException e) {
          throw new ServletException("Erro ao processar pagamento", e);
        }

        // 3. Simular pagamento
        pagamento.setTipoPagamento(pagamentoRequest.getTipoPagamento());
        pagamento.setValor(valorTotal);

      
        System.out.println("Tipo de pagamento recebido (raw): '" + pagamento.getTipoPagamento() + "'");

        String tipo = pagamento.getTipoPagamento();
      
        if (tipo == null)
            tipo = "";
        tipo = tipo.toLowerCase().replaceAll("\\s+", ""); 
        
        

        switch (tipo) { // <-- aqui usa a variável 'tipo' já limpa e normalizada
            case "boleto":
                pagamento.setTitulo(pagamento.getTitulo());
                pagamento.setStatus("pendente");
                pagamento.setMensagem("Boleto gerado: 00190.00009 01234.567890 12345.678901 1 23450000010000");
                break;
            case "pix":
                pagamento.setTitulo(pagamento.getTitulo());
                pagamento.setStatus("aprovado");
                pagamento.setMensagem("Pagamento aprovado via PIX.");
                break;
            case "cartaocredito": // funciona mesmo que venha "cartão de crédito"
                if (pagamento.getValor() <= 1000.00) {
                    pagamento.setTitulo(pagamento.getTitulo());
                    pagamento.setStatus("aprovado");
                    pagamento.setMensagem("Pagamento com cartão aprovado!");
                } else {
                    pagamento.setTitulo(pagamento.getTitulo());
                    pagamento.setStatus("rejeitado");
                    pagamento.setMensagem("Pagamento recusado: limite insuficiente.");
                }
                break;
            default:
                pagamento.setStatus("erro");
                pagamento.setMensagem("Tipo de pagamento inválido.");
                break;
        }
        enviarResposta(response, pagamento);
    }

    private void enviarResposta(HttpServletResponse response, Pagamento pagamento) throws IOException, java.io.IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(pagamento));
    }

    
}
