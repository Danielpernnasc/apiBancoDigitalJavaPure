package com.api.apiBookStore.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.api.apiBookStore.dao.BookStoreDAO;
import com.api.apiBookStore.model.Livros;
import com.api.apiBookStore.service.LivrosService;
import com.google.gson.Gson;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LivrosServlet extends HttpServlet{

    private final BookStoreDAO livrosDAO = new BookStoreDAO();
    private final LivrosService livrosService = new LivrosService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

      
            try {
                if (pathInfo == null || pathInfo.equals("/")) {
                        List<Livros> livrosList = livrosDAO.listarLivros();
                        out.print(new Gson().toJson(livrosList));
                } else {
                    String param = pathInfo.substring(1); // remove o "/"
                    List<Livros> resultado = new ArrayList<>();

                    resultado.addAll(livrosDAO.buscarLivrosPorTitulo(param));
                    resultado.addAll(livrosDAO.buscarLivrosPorAutor(param));
                    resultado.addAll(livrosDAO.buscarLivrosPorGenero(param));
                    resultado.addAll(livrosDAO.buscarLivrosPorISBN(param));
                    resultado.addAll(livrosDAO.buscarLivrosPorEditora(param));

                    if (resultado.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.write("{\"mensagem\": \"Nenhum livro encontrado.\"}");
                    } else {
                        out.print(new Gson().toJson(resultado));
                    }
                }
            } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error retrieving book list: " + e.getMessage());
        } 
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Livros livro = new Gson().fromJson(request.getReader(), Livros.class);
            livrosService.createBook(livro);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erro ao criar livro: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao criar livro: " + e.getMessage());

        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Livros livro = new Gson().fromJson(request.getReader(), Livros.class);
            livrosService.updateBook(livro);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erro ao atualizar livro: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Livro ID é obrigatório na URL");
            return;
        }
        try {
            long livroId = Long.parseLong(pathInfo.substring(1)); // remove o "/"
            livrosService.deleteBookById(livroId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID do livro inválido: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao deletar livro: " + e.getMessage());
        }
    }

    public void doGetForTitle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titulo = request.getParameter("titulo");
        if (titulo == null || titulo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Título do livro é obrigatório");
            return;
        }
        try {
            Livros livro = livrosService.getBookByTitle(titulo);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(livro));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao buscar livro por título: " + e.getMessage());
        }
    }

    public void doGetForAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String autor = request.getParameter("autor");
        if (autor == null || autor.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Autor do livro é obrigatório");
            return;
        }
        try {
            Livros livro = livrosService.getBookbyAuthor(autor);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(livro));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao buscar livro por autor: " + e.getMessage());
        }
    }
    public void doGetForGenre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String genero = request.getParameter("genero");
        if (genero == null || genero.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Gênero do livro é obrigatório");
            return;
        }
        try {
            Livros livro = livrosService.getBookbyGenrer(genero);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(livro));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao buscar livro por gênero: " + e.getMessage());
        }
    }
    public void doGetForIsbn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        if (isbn == null || isbn.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ISBN do livro é obrigatório");
            return;
        }
        try {
            Livros livro = livrosService.getBookbyIsbn(isbn);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(livro));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao buscar livro por ISBN: " + e.getMessage());
        }
    }
    public void doGetForPublisher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String editora = request.getParameter("editora");
        if (editora == null || editora.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Editora do livro é obrigatória");
            return;
        }
        try {
            Livros livro = livrosService.getBookByPublisher(editora);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(livro));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao buscar livro por editora: " + e.getMessage());
        }
    }


}
