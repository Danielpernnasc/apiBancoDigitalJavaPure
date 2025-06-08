package com.api.apiBanco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.apiBanco.model.Client;
import com.api.apiBanco.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> listarTodos() {
        return clientService.listarTodos();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> buscarForEmail(@PathVariable("email") String email) {
        return clientService.buscarForEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> buscarForIdCliente(@PathVariable("id") Long id) {
        try {
            Client client = clientService.buscarForIdCliente(id);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Client salve(@RequestBody Client client) {
        return clientService.salve(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") Long id, @RequestBody Client clientAtualizado) {
        try {
            Client client = clientService.update(id, clientAtualizado);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        try {
            clientService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
