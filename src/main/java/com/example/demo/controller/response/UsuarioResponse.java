package com.example.demo.controller.response;

public class UsuarioResponse {
    private Long id;

    public UsuarioResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
