package com.example.demo.controller.request;

public class UsuarioRequest {
    private String nome;
    private String nickname;
    private String senha;
    private int idade;

    public UsuarioRequest(String nome, String nickname, String senha, int idade) {
        this.nome = nome;
        this.nickname = nickname;
        this.senha = senha;
        this.idade = idade;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
