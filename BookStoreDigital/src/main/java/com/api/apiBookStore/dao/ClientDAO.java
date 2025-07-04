package com.api.apiBookStore.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api.apiBookStore.model.Client;
import com.api.apiBookStore.model.LoginRequest;

public class ClientDAO {
    private final String jbcURL =  "jdbc:mysql://mydigitalbank.c7sog0s4qdes.us-east-2.rds.amazonaws.com:3306/BookStoreDB";
    private final String user = "adminDigBank";
    private final String pass = "Dce81125";

    public List<Client> listarClientes() throws SQLException {
        List<Client> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM clientes");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
                client.setRepeatpassword(rs.getString("repeatpassword"));
                client.setCpf(rs.getString("cpf"));
                client.setEndereco(rs.getString("endereco"));
                client.setDataCriacao(rs.getDate("dataCriacao"));
                lista.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            throw e;
        }
        System.out.println("Total de clientes encontrados: " + lista.size());
        return lista;
    }

    public void cadastrarCliente(Client client) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para cadastro!");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO clientes (name, email, password, repeatpassword, cpf, endereco, dataCriacao) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPassword());
            stmt.setString(4, client.getRepeatpassword());
            stmt.setString(5, client.getCpf());
            stmt.setString(6, client.getEndereco());
            stmt.setDate(7, new java.sql.Date(client.getDataCriacao().getTime()));
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");
        }
    }

    public Client loginClient(LoginRequest loginRequest) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;



        try {

            connection = DriverManager.getConnection(jbcURL, user, pass);

            String sql = "SELECT * FROM clientes WHERE email = ? AND password = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, loginRequest.getEmail());

            preparedStatement.setString(2, loginRequest.getPassword());

            resultSet = preparedStatement.executeQuery();



            if (resultSet.next()) {

                Client client = new Client();

                client.setId(resultSet.getInt("id"));

                client.setName(resultSet.getString("name"));

                client.setEmail(resultSet.getString("email"));

                

                return client;

            }

        } finally {

            if (resultSet != null) resultSet.close();

            if (preparedStatement != null) preparedStatement.close();

            if (connection != null) connection.close();

        }

        return null;

    }
    

    public Client getClientById(Client clientId) {
        Client foundClient = null;
        String sql = "SELECT * FROM clientes WHERE id = ?";
    
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, clientId.getId());
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    foundClient = new Client();
                    foundClient.setId(rs.getLong("id"));
                    foundClient.setName(rs.getString("name"));
                    foundClient.setEmail(rs.getString("email"));
                    foundClient.setPassword(rs.getString("password"));
                    foundClient.setRepeatpassword(rs.getString("repeatpassword"));
                    foundClient.setCpf(rs.getString("cpf"));
                    foundClient.setEndereco(rs.getString("endereco"));
                    foundClient.setDataCriacao(rs.getDate("dataCriacao"));
        
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
    
        return foundClient;
    }

    public Client updatedClient(Client clientId) {
        Client updatedClient = null;
        String sql = "UPDATE clientes SET nome = ?, email = ?, password = ?, repeatpassword = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, clientId.getName());
            stmt.setString(2, clientId.getEmail());
            stmt.setString(3, clientId.getPassword());
            stmt.setString(4,clientId.getRepeatpassword());
            stmt.setString(5, clientId.getCpf());
            stmt.setString(6, clientId.getEndereco());
            stmt.setDate(7, new java.sql.Date(clientId.getDataCriacao().getTime()));
            stmt.setLong(8, clientId.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updatedClient = clientId; // Return the updated client object
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
        
        return updatedClient;
    }

    public void deleteClient(Client client) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, client.getId());
            stmt.executeUpdate();
            System.out.println("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            throw e;
        }
    }

    public ClientDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver carregado com sucesso.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver: " + e.getMessage());
        }
    }
}
   

