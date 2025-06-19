package com.api.apiBanco.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.api.apiBanco.dao.ClientDAO;
import com.api.apiBanco.model.Client;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientServlet extends HttpServlet{
    private final ClientDAO clientDAO = new ClientDAO();

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Client> clientList = clientDAO.listarClientes();
            System.out.println("Total de clientes: " + clientList.size()); // Debug
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(clientList));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error retrieving client list: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            Client client = new Gson().fromJson(json, Client.class);
            clientDAO.cadastrarCliente(client); 
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Client created successfully");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver carregado com sucesso.");
            } catch (ClassNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error loading database driver: " + e.getMessage());
                return;
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error creating client: " + e.getMessage());
        }
    }

}
