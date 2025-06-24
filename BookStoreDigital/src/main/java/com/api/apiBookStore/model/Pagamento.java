package com.api.apiBookStore.model;

public class Pagamento {
    private String tipoPagamento;
    private double valor;
    private String status; // aprovado, pendente, rejeitado
    private String mensagem; // ex: "Aguardando pagamento do boleto", etc.

 
   
    public String getTipoPagamento() {
      
   
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {

        this.tipoPagamento = tipoPagamento;

    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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
}
