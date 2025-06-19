package com.api.apiBanco.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api.apiBanco.model.Client;

public class ClientDAO {
    private final String jbcURL =  "jdbc:mysql://mydigitalbank.c7sog0s4qdes.us-east-2.rds.amazonaws.com:3306/mydigitalbank";
    private final String user = "adminDigBank";
    private final String pass = "Dce81125";

    public List<Client> listarClientes() throws SQLException {
        List<Client> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco!");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM client");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setNome(rs.getString("nome"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
                client.setRepeatpassword(rs.getString("repeatpassword"));
                lista.add(client);
            }
        }
        System.out.println("Total de clientes encontrados: " + lista.size());
        return lista;
    }

    public void cadastrarCliente(Client client) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jbcURL, user, pass)) {
            System.out.println("Conectado ao banco para cadastro!");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO client (nome, email, password, repeatpassword) VALUES (?, ?, ?, ?)");
            stmt.setString(1, client.getNome());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPassword());
            stmt.setString(4, client.getRepeatpassword());
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");
        }
    }
}
   

