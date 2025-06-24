package com.api.apiBookStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api.apiBookStore.model.Livros;
import com.api.apiBookStore.model.StamentBook;

public class BookStoreDAO {
    private final String jbcURL = "jdbc:mysql://mydigitalbank.c7sog0s4qdes.us-east-2.rds.amazonaws.com:3306/BookStoreDB";
    private final String user = "adminDigBank";
    private final String pass = "Dce81125";

    private Livros montarLivro(ResultSet rs) throws SQLException {
        Livros livro = new Livros();
        livro.setId(rs.getLong("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setEditora(rs.getString("editora"));
        livro.setAnoPublicacao(rs.getString("anoPublicacao"));
        livro.setGenero(rs.getString("genero"));
        livro.setSinopse(rs.getString("sinopse"));
        livro.setIsbn(rs.getString("isbn"));
        livro.setIdioma(rs.getString("idioma"));
        livro.setPreco(rs.getDouble("preco"));
        livro.setImageUrl(rs.getString("imageUrl"));
        livro.setQuant(rs.getInt("quant"));
        return livro;
    }

    private StamentBook stamentBook = new StamentBook();


   

    public List<Livros> listarLivros() throws SQLException {
        List<Livros> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livros livro = montarLivro(rs);
                lista.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de livros encontrados: " + lista.size());
        return lista;
    }

    public void cadastrarLivros(Livros books) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para cadastro!");
            stamentBook.statementInput(conn, books);
            System.out.println("Livro cadastrado com sucesso!");
        }
    }

    public void atualizarLivro(Livros books, Long livroId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para atualização!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros WHERE id = ?");
            stmt.setLong(1, livroId);
            stamentBook.updateLivro(conn, books);
            System.out.println("Livro atualizado com sucesso!");
        }
    }

    public void excluirLivro(long livroId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para exclusão!");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM livros WHERE id = ?");
            stmt.setLong(1, livroId);
            stmt.executeUpdate();
            System.out.println("Livro excluído com sucesso!");
        }
    }

    public List<Livros> buscarLivrosPorTitulo(String livroTitle) throws SQLException {
        List<Livros> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para busca por título!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros WHERE titulo LIKE ?");
            stmt.setString(1, "%" + livroTitle + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarLivro(rs));
            
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de livros encontrados: " + lista.size());
        return lista;
    }   

    public List<Livros> buscarLivrosPorAutor(String autor) throws SQLException {
        List<Livros> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para busca por autor!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros WHERE autor LIKE ?");
            stmt.setString(1, "%" + autor + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarLivro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de livros encontrados: " + lista.size());
        return lista;
    }

    public List<Livros> buscarLivrosPorGenero(String genero) throws SQLException {
        List<Livros> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para busca por gênero!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros WHERE genero LIKE ?");
            stmt.setString(1, "%" + genero + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarLivro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de livros encontrados: " + lista.size());
        return lista;
    }

    public List<Livros> buscarLivrosPorEditora(String editora) throws SQLException {
        List<Livros> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para busca por editora!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros WHERE editora LIKE ?");
            stmt.setString(1, "%" + editora + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarLivro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de livros encontrados: " + lista.size());
        return lista;
    } 

    public List<Livros> buscarLivrosPorISBN(String isbn) throws SQLException {
        List<Livros> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para busca por ano de publicação!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livros WHERE isbn LIKE ?");
            stmt.setString(1, "%" + isbn + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarLivro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de livros encontrados: " + lista.size());
        return lista;
    }

    public BookStoreDAO(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver MySQL: " + e.getMessage());
        }
    }


}
