package com.api.apiBookStore.model;

import java.util.List;

import com.api.apiBookStore.DTO.ItemSacola;

import jakarta.persistence.Transient;

public class Pagamento {

    private Long id;
    private String titulo;
    private List<ItemSacola> sacola;
    private String tipoPagamento;
    private Double valor;
    private String status; // aprovado, pendente, rejeitado
    private String mensagem; // ex: "Aguardando pagamento do boleto", etc.

    @Transient
    private Client clientId = new Client();

    @Transient
    private String cpf;

    public Long getClienteId(){
        return id;
    }

    public void setClienteId(Long id) {
        this.id = id;
    }

    public List<ItemSacola> getSacola() {
        return sacola;
    }

    public void setSacola(List<ItemSacola> sacola) {
        this.sacola = sacola;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getTipoPagamento() {
      
   
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {

        this.tipoPagamento = tipoPagamento;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }   

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

  

  
}
