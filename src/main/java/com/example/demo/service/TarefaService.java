package com.example.demo.service;

import com.example.demo.controller.request.TarefaRequest;
import com.example.demo.controller.response.TarefaDetalhadoResponse;
import com.example.demo.controller.response.TarefaResponse;
import com.example.demo.entity.Tarefa;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.TarefaJaExisteException;
import com.example.demo.exception.TarefaNaoExisteException;
import com.example.demo.exception.UsuarioNaoExisteException;
import com.example.demo.mapper.TarefaMapper;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarefaService {

    private static final String TAREFA_NAO_EXISTE = "Tarefa não existe!";
    @Autowired
    private TarefaRepository repository;

    public TarefaResponse criar(TarefaRequest request) {
        Tarefa jaExiste = repository.findByTitulo(request.getTitulo());

        if (jaExiste != null) {
            throw new TarefaJaExisteException("Tarefa já existe!");
        }

        Tarefa tarefa = TarefaMapper.toEntity(request);
        repository.save(tarefa);
        return TarefaMapper.toResponse(tarefa);
    }

    public TarefaDetalhadoResponse detalhar(Long id) {
        Optional<Tarefa> tarefa = repository.findById(id);

        if (tarefa.isEmpty()) {
            throw new TarefaNaoExisteException(TAREFA_NAO_EXISTE);
        }

        return TarefaMapper.toResponseDetalhado(tarefa.get());
    }

    public TarefaResponse atualizar(TarefaRequest request, Long id) {
        Optional<Tarefa> tarefaOp = repository.findById(id);

        if (tarefaOp.isEmpty()) {
            throw new TarefaNaoExisteException(TAREFA_NAO_EXISTE);
        }

        Tarefa tarefa = tarefaOp.get();

        tarefa.setDescricao(request.getDescricao());
        tarefa.setStatus(request.getStatus());
        tarefa.setTitulo(request.getTitulo());

        return TarefaMapper.toResponse(tarefa);
    }

    public TarefaResponse deletar(Long id) {
        Optional<Tarefa> tarefaOp = repository.findById(id);

        if (tarefaOp.isEmpty()) {
            throw new TarefaNaoExisteException(TAREFA_NAO_EXISTE);
        }

        Tarefa tarefa = tarefaOp.get();

        tarefa.setAtivo(false);

        repository.save(tarefa);

        return TarefaMapper.toResponse(tarefa);
    }
}
