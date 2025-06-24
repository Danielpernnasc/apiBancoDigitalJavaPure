package com.api.apiBookStore.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.api.apiBookStore.dao.ClientDAO;
import com.api.apiBookStore.model.Client;
import com.api.apiBookStore.model.LoginRequest;
import com.api.apiBookStore.service.ClientService;
import com.api.apiBookStore.util.JwUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ClientServlet extends HttpServlet{
 
    private final ClientDAO clientDAO = new ClientDAO();
     private final ClientService clientService = new ClientService();

    private boolean isTokenValid(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String authHeader = request.getHeader("Authorization");
         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente ou inválido");
             return false;
         }

         String token = authHeader.substring(7);
         if (!JwUtil.validateToken(token)) {
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
             return false;
         }

         return true;
     }


     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         String pathInfo = request.getPathInfo();

         if (pathInfo == null || pathInfo.equals("/")) {
             try {
                 List<Client> clientList = clientDAO.listarClientes();
                 response.setContentType("application/json");
                 PrintWriter out = response.getWriter();
                 out.print(new Gson().toJson(clientList));
             } catch (SQLException e) {
                 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                 response.getWriter().write("Error retrieving client list: " + e.getMessage());
             }
         } else {
             // Existe ID na URL
             String[] pathParts = pathInfo.split("/");
             if (pathParts.length < 2) {
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client ID is required");
                 return;
             }

             try {
                 int clientId = Integer.parseInt(pathParts[1]);
                 Client clientToSearch = new Client();
                 clientToSearch.setId((long) clientId);

                 Client client = clientService.getClientById(clientToSearch);

                 response.setContentType("application/json");
                 PrintWriter out = response.getWriter();
                 out.print(new Gson().toJson(client));
             } catch (NumberFormatException e) {
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Client ID format");
             } catch (Exception e) {
                 response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                 response.getWriter().write("Error finding client: " + e.getMessage());
             }
         }
     }

     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {

         String pathInfo = request.getPathInfo(); // ex: /login ou /

         if ("/login".equals(pathInfo)) {
             doPostLogin(request, response); // 🔵 NÃO VERIFICA TOKEN AQUI
             return;
         }

         if (pathInfo == null || pathInfo.equals("/")) {
             // 🔓 Não exige token para cadastro de novo cliente
             String json = request.getReader().lines().reduce("", (acc, line) -> acc + line);
             Client client = new Gson().fromJson(json, Client.class);
             try {
                 clientService.createClient(client);
                 response.setStatus(HttpServletResponse.SC_CREATED);
                 response.getWriter().write("Client created successfully");
             } catch (Exception e) {
                 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                 response.getWriter().write("Error creating client: " + e.getMessage());
             }
             return;
         }

         // Todas as outras rotas POST precisam de token
         if (!isTokenValid(request, response))
             return;

         response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid POST path");
     }

     private void doPostLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String json = request.getReader().lines().reduce("", (acc, line) -> acc + line);
         LoginRequest loginRequest = new Gson().fromJson(json, LoginRequest.class);

         try {
             String token = clientService.loginClientAndGenerateToken(loginRequest);
             response.setStatus(HttpServletResponse.SC_OK);
             response.setContentType("application/json");
             response.getWriter().write("{\"token\": \"" + token + "\"}");
         } catch (Exception e) {
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
         }
     }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if (!isTokenValid(request, response)) return;
        String pathInfo = request.getPathInfo(); // exemplo: /20
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client ID is required in the URL");
            return;
        }
    
        long clientId;
        try {
            clientId = Integer.parseInt(pathInfo.substring(1)); // remove o `/`
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
            return;
        }

        String json = request.getReader().lines().reduce("", (acc, line) -> acc + line);
        Client client = new Gson().fromJson(json, Client.class);
        client.setId((long) clientId); // sobrescreve o id recebido no JSON com o da URL
    
        try {
            clientService.updateClient(client);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Client updated successfully");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating client: " + e.getMessage());
        }
    
    }
    
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if (!isTokenValid(request, response)) return;
        String pathInfo = request.getPathInfo(); // exemplo: /20
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client ID is required in the URL");
            return;
        }
    
        long clientId;
        try {
            clientId = Integer.parseInt(pathInfo.substring(1)); // remove o `/`
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
            return;
        }
    
        Client clientToDelete = new Client();
        clientToDelete.setId((long) clientId);
    
        try {
            clientService.deleteClient(clientToDelete);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error deleting client: " + e.getMessage());
        }
    }


}
