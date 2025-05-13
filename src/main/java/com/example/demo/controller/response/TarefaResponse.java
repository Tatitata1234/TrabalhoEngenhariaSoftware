package com.example.demo.controller.response;

public class TarefaResponse {
    private Long id;

    public TarefaResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
