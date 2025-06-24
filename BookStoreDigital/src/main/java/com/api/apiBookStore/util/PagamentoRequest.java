package com.api.apiBookStore.util;

import java.util.List;

import com.api.apiBookStore.DTO.ItemSacola;

public class PagamentoRequest {
    private long clienteId;
    private String tipoPagamento;
    private List<ItemSacola> sacola;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public List<ItemSacola> getSacola() {
        return sacola;
    }
    
    public void setSacola(List<ItemSacola> sacola) {
        this.sacola = sacola;
    }
}
