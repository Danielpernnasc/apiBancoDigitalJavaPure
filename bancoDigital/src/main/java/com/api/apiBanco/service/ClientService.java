package com.api.apiBanco.service;
import java.util.List;

import com.api.apiBanco.dao.ClientDAO;
import com.api.apiBanco.model.Client;

public class ClientService {
    private ClientDAO clientDAO = new ClientDAO();

 public List<Client> getAllClients() throws Exception {
    List<Client> clients = clientDAO.listarClientes();
    if (clients == null || clients.isEmpty()) {
        throw new Exception("Nenhum cliente encontrado");
    }
    return clients;
}

public Client getClientById(Client clientId) throws Exception {
    
    if (clientId == null) {
        throw new Exception("ID do Cliente é obrigatório e não pode ser nulo");
    }
    Client client = clientDAO.getClientById(clientId);
    if (client == null) {
        throw new Exception("Cliente não encontrado com o ID fornecido");
    }
    return client;
}


public void createClient(Client client) throws Exception {

    if(client == null){
        throw new Exception("Cliente não pode ter campos nulos");

    }

    if(client.getName() == null || client.getName().isEmpty()){
        throw new Exception("Nome do Cliente é obrigatório e não pode ser nulo ou vazio");
    }

    if(client.getEmail() == null || client.getEmail().isEmpty()){
        throw new Exception("Email do cliente é obrigatório e não pode ser nulo ou vazio");
    }

    if(client.getPassword() == null || client.getPassword().isEmpty()){
        throw new Exception("Senha do cliente é obrigatório e não pode ser nula ou vazia");
    }

    if(client.getRepeatpassword() == null || client.getRepeatpassword().isEmpty()){
        throw new Exception("Repetição de senha do cliente é obrigatório e não pode ser nula ou vazia");
    }

    clientDAO.cadastrarCliente(client);
}

    public void updateClient(Client client) throws Exception {
        if (client == null || client.getId() == null) {
            throw new Exception("ID do Cliente é obrigatório e não pode ser nulo");
        }
        clientDAO.updatedClient(client);
    }

    public void deleteClient(Client client) throws Exception {
        if (client == null || client.getId() == null) {
            throw new Exception("ID do Cliente é obrigatório e não pode ser nulo");
        }
        clientDAO.deleteClient(client);
    }


}
