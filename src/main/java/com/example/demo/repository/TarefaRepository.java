package com.example.demo.repository;

import com.example.demo.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Tarefa findByTitulo(String titulo);
}
