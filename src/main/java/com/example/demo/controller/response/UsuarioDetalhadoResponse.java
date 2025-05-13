package com.example.demo.controller.response;

public class UsuarioDetalhadoResponse {
    private Long id;
    private String nome;
    private String nickname;
    private int idade;

    public UsuarioDetalhadoResponse(Long id, String nome, String nickname, int idade) {
        this.id = id;
        this.nome = nome;
        this.nickname = nickname;
        this.idade = idade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
