package com.example.demo.controller.request;

public class TarefaRequest {
    private String titulo;
    private String status;
    private String descricao;
    private Long userId;

    public TarefaRequest(String titulo, String status, String descricao, Long userId) {
        this.titulo = titulo;
        this.status = status;
        this.descricao = descricao;
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
