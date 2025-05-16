package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@SQLRestriction("ativo")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String nickname;
    private String senha;
    private int idade;
    private boolean ativo;

    @OneToMany(mappedBy = "usuario")
    private List<Tarefa> tarefas;

    public Usuario(String nome, String nickname, String senha, int idade) {
        this.nome = nome;
        this.nickname = nickname;
        this.senha = senha;
        this.idade = idade;
        this.ativo = true;
    }

    public Usuario() {
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
