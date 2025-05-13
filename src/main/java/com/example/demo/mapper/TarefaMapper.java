package com.example.demo.mapper;

import com.example.demo.controller.request.TarefaRequest;
import com.example.demo.controller.response.TarefaDetalhadoResponse;
import com.example.demo.controller.response.TarefaResponse;
import com.example.demo.entity.Tarefa;

public class TarefaMapper {

    public static TarefaResponse toResponse(Tarefa tarefa) {
        return new TarefaResponse(tarefa.getId());
    }

    public static Tarefa toEntity(TarefaRequest request) {
        return new Tarefa(request.getTitulo(), request.getStatus(), request.getDescricao());
    }

    public static TarefaDetalhadoResponse toResponseDetalhado(Tarefa tarefa) {
        return new TarefaDetalhadoResponse(tarefa.getId(), tarefa.getTitulo(), tarefa.getStatus(), tarefa.getDescricao());
    }
}
