package com.example.demo.controller.response;

public class TarefaDetalhadoResponse {
    private Long id;
    private String titulo;
    private String status;
    private String descricao;

    public TarefaDetalhadoResponse(Long id, String titulo, String status, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.status = status;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
