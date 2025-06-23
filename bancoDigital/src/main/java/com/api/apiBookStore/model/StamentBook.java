package com.api.apiBookStore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StamentBook {
   
        public void statementInput(Connection conn, Livros book) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, editora, anoPublicacao, genero, sinopse, isbn, idioma, preco, imageUrl) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitulo());
            stmt.setString(2, book.getAutor());
            stmt.setString(3, book.getEditora());
            stmt.setString(4, book.getAnoPublicacao());
            stmt.setString(5, book.getGenero());
            stmt.setString(6, book.getSinopse());
            stmt.setString(7, book.getIsbn());
            stmt.setString(8, book.getIdioma());
            stmt.setDouble(9, book.getPreco());
            stmt.setString(10, book.getImageUrl());
            stmt.executeUpdate();
        }
    }
}
