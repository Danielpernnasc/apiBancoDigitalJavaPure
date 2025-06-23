package com.api.apiBookStore.service;

import java.util.List;

import com.api.apiBookStore.dao.BookStoreDAO;
import com.api.apiBookStore.model.Livros;

public class LivrosService {

    private BookStoreDAO livrosDAO = new BookStoreDAO();
    /**
     * Retorna uma lista de todos os livros disponíveis.
     *
     * @return Lista de livros.
     * @throws Exception Se não houver livros disponíveis.
     */
    public List<String> getAllBooks() throws Exception {
        List<Livros> livros = livrosDAO.listarLivros();
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Nenhum livro encontrado");
        }
        return livros.stream()
                     .map(Livros::getTitulo) // Assuming Livros has a getTitulo() method
                     .toList();
    }

    /**
     * Retorna um livro específico pelo ID.
     *
     * @param livroId ID do livro a ser buscado.
     * @return Livro correspondente ao ID fornecido.
     * @throws Exception Se o livro não for encontrado.
     */
    public Livros getBookByTitle(String livroTitle) throws Exception {
        if (livroTitle == null || livroTitle.isEmpty()) {
            throw new Exception("ID do Livro é obrigatório e deve ser maior que zero");
        }
        List<Livros> livros = livrosDAO.buscarLivrosPorTitulo(livroTitle);
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Livro não encontrado com o título fornecido");
        }
        return livros.get(0); // Return the first book from the list
    }

    /**
     * Cria um novo livro.
     *
     * @param livro Livro a ser criado.
     * @throws Exception Se o livro tiver campos nulos ou inválidos.
     */
    public void createBook(Livros livro) throws Exception {
        if (livro == null) {
            throw new Exception("Livro não pode ter campos nulos");
        }

        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new Exception("Título do Livro é obrigatório e não pode ser nulo ou vazio");
        }

        if (livro.getAutor() == null || livro.getAutor().isEmpty()) {
            throw new Exception("Autor do Livro é obrigatório e não pode ser nulo ou vazio");
        }

        if (livro.getEditora() == null || livro.getEditora().isEmpty()) {
            throw new Exception("Editora do Livro é obrigatória e não pode ser nula ou vazia");
        }

        if (livro.getAnoPublicacao() == null || livro.getAnoPublicacao().isEmpty()) {
            throw new Exception("Ano de Publicação do Livro é obrigatório e não pode ser nulo ou vazio");
        }

        if (livro.getGenero() == null || livro.getGenero().isEmpty()) {
            throw new Exception("Gênero do Livro é obrigatório e não pode ser nulo ou vazio");
        }
        if (livro.getSinopse() == null || livro.getSinopse().isEmpty()) {
            throw new Exception("Sinopse do Livro é obrigatória e não pode ser nula ou vazia");
        }
        if (livro.getIsbn() == null || livro.getIsbn().isEmpty()) {
            throw new Exception("ISBN do Livro é obrigatório e não pode ser nulo ou vazio");
        }
        if (livro.getIdioma() == null || livro.getIdioma().isEmpty()) {
            throw new Exception("Idioma do Livro é obrigatório e não pode ser nulo ou vazio");
        }

        if (livro.getPreco() <= 0) {
            throw new Exception("Preço do Livro deve ser maior que zero");
        }

        livrosDAO.cadastrarLivros(livro);
    }

    /**
     * Atualiza um livro existente.
     *
     * @param livro Livro a ser atualizado.
     * @throws Exception Se o livro não for encontrado ou tiver campos inválidos.
     */
    public Livros getLivrosByTitle(String title) throws Exception {
        if (title == null || title.isEmpty()) {
            throw new Exception("Gênero do Livro é obrigatório e não pode ser nulo ou vazio");
        }
        List<Livros> livros = livrosDAO.buscarLivrosPorTitulo(title);
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Nenhum livro encontrado com o gênero fornecido");
        }
        return livros.get(0); // Return the first book from the list
    }

    public Livros getBookbyAuthor(String autor) throws Exception {
        if (autor == null || autor.isEmpty()) {
            throw new Exception("Autor do Livro é obrigatório e não pode ser nulo ou vazio");
        }
        List<Livros> livros = livrosDAO.buscarLivrosPorAutor(autor);
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Nenhum livro encontrado com o autor fornecido");
        }
        return livros.get(0); // Return the first book from the list
    }

    public Livros getBookbyGenrer(String genero) throws Exception {
        if (genero == null || genero.isEmpty()) {
            throw new Exception("Gênero do Livro é obrigatório e não pode ser nulo ou vazio");
        }
        List<Livros> livros = livrosDAO.buscarLivrosPorGenero(genero);
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Nenhum livro encontrado com o gênero fornecido");
        }
        return livros.get(0); // Return the first book from the list
    }

    public Livros getBookbyIsbn(String isbn) throws Exception {
        if (isbn == null || isbn.isEmpty()) {
            throw new Exception("ISBN do Livro é obrigatório e não pode ser nulo ou vazio");
        }
        List<Livros> livros = livrosDAO.buscarLivrosPorISBN(isbn);
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Nenhum livro encontrado com o ISBN fornecido");
        }
        return livros.get(0); // Return the first book from the list
    }

    public Livros getBookByPublisher(String editora) throws Exception {
        if (editora == null || editora.isEmpty()) {
            throw new Exception("Editora do Livro é obrigatória e não pode ser nula ou vazia");
        }
        List<Livros> livros = livrosDAO.buscarLivrosPorEditora(editora);
        if (livros == null || livros.isEmpty()) {
            throw new Exception("Nenhum livro encontrado com a editora fornecida");
        }
        return livros.get(0); // Return the first book from the list
    }

    public void updateBook(Livros livro) throws Exception {
        if (livro == null || livro.getId() <= 0) {
            throw new Exception("Livro não pode ter campos nulos ou ID inválido");
        }
        livrosDAO.atualizarLivro(livro);
    }

   
    public void deleteBookById(long livroId) throws Exception {
        if (livroId <= 0) {
            throw new Exception("ID do Livro é obrigatório e deve ser maior que zero");
        }
        livrosDAO.excluirLivro(livroId);
    }
}
