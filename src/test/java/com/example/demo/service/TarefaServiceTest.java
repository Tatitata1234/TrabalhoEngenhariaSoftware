package com.example.demo.service;

import com.example.demo.controller.request.TarefaRequest;
import com.example.demo.controller.response.TarefaDetalhadoResponse;
import com.example.demo.controller.response.TarefaResponse;
import com.example.demo.entity.Tarefa;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.TarefaJaExisteException;
import com.example.demo.exception.TarefaNaoExisteException;
import com.example.demo.exception.UsuarioNaoExisteException;
import com.example.demo.repository.TarefaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {
    @Mock
    private TarefaRepository mockTaskRepository;
    @Mock
    private UsuarioRepository mockUserRepository;

    @InjectMocks
    private TarefaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarHappyPath() {
        Usuario user = new Usuario("test", "test", "test", 20);

        when(this.mockTaskRepository.findByTitulo(any())).thenReturn(null);
        when(this.mockUserRepository.findById(any())).thenReturn(Optional.of(user));

        TarefaRequest req = new TarefaRequest(
                "a",
                "a",
                "a",
                1L
        );
        assertDoesNotThrow(() -> { this.service.criar(req); });
        verify(this.mockTaskRepository).findByTitulo(req.getTitulo());
        verify(this.mockUserRepository).findById(req.getUserId());
        verify(this.mockTaskRepository).save(any());
    }

    @Test
    void criarTaskExists() {
        Usuario user = new Usuario("test", "test", "test", 20);

        when(this.mockTaskRepository.findByTitulo(any())).thenReturn(new Tarefa());

        TarefaRequest req = new TarefaRequest(
                "a",
                "a",
                "a",
                0L
        );
        assertThrows(TarefaJaExisteException.class, () -> {
            this.service.criar(req);
        });
        verify(this.mockTaskRepository).findByTitulo(req.getTitulo());
    }

    @Test
    void criarUserIsEmpty() {
        Usuario user = new Usuario("test", "test", "test", 20);

        when(this.mockTaskRepository.findByTitulo(any())).thenReturn(null);
        when(this.mockUserRepository.findById(any())).thenReturn(Optional.empty());

        TarefaRequest req = new TarefaRequest(
                "a",
                "a",
                "a",
                0L
        );
        assertThrows(UsuarioNaoExisteException.class, () -> {
            this.service.criar(req);
        });
        verify(this.mockTaskRepository).findByTitulo(req.getTitulo());
        verify(this.mockUserRepository).findById(req.getUserId());
    }

    @Test
    void detalharNotEmpty() {
        Tarefa task = new Tarefa("a", "a", "a");
        task.setId(0L);
        when(mockTaskRepository.findById(any())).thenReturn(Optional.of(task));

        TarefaDetalhadoResponse resp = this.service.detalhar(0L);
        verify(this.mockTaskRepository).findById(task.getId());
        assertEquals(task.getId(), resp.getId());
    }

    @Test
    void detalharEmpty() {
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoExisteException.class, () -> {
            this.service.detalhar(0L);
        });
        verify(this.mockTaskRepository).findById(0L);
    }

    @Test
    void atualizarHappyPath() {
        Tarefa task = new Tarefa("a", "a", "a");
        task.setId(0L);
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.of(task));

        TarefaRequest req = new TarefaRequest("b", "b", "b", 0L);

        TarefaDetalhadoResponse resp = this.service.atualizar(req, 0L);
        verify(this.mockTaskRepository).findById(task.getId());
        verify(this.mockTaskRepository).save(any());

        assertEquals(req.getTitulo(), resp.getTitulo());
        assertEquals(req.getStatus(), resp.getStatus());
        assertEquals(req.getDescricao(), resp.getDescricao());
    }

    @Test
    void atualizarTaskIsEmpty() {
        TarefaRequest req = new TarefaRequest("b", "b", "b", 0L);
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoExisteException.class, () -> {
            this.service.atualizar(req, 0L);
        });
        verify(this.mockTaskRepository).findById(any());
    }

    @Test
    void deletarHappyPath() {
        Tarefa task = new Tarefa("a", "a", "a");

        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.of(task));

        this.service.deletar(0L);
        verify(this.mockTaskRepository).findById(any());
        verify(this.mockTaskRepository).save(any());
    }

    @Test
    void deletarTaskIsEmpty() {
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoExisteException.class, () -> {
            this.service.deletar(0L);
        });
        verify(this.mockTaskRepository).findById(any());
    }

    @Test
    void listarPorUsuarioHappyPath() {
        Usuario user = new Usuario("a", "a", "a", 20);
        List<Tarefa> tasks = new LinkedList<Tarefa>(
                Arrays.asList(
                        new Tarefa("a", "a", "a"),
                        new Tarefa("b", "b", "b")
                )
        );

        when(this.mockUserRepository.findById(any())).thenReturn(Optional.of(user));
        when(this.mockTaskRepository.findByUsuarioId(any())).thenReturn(tasks);

        List<TarefaDetalhadoResponse> resp = this.service.listarPorUsuario(0L);
        assertEquals(tasks.size(), resp.size());

        for(int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).getTitulo(), resp.get(i).getTitulo());
        }
        verify(this.mockUserRepository).findById(any());
        verify(this.mockTaskRepository).findByUsuarioId(any());
    }

    @Test
    void listarPorUsuarioUserIsEmpty() {
        when(this.mockUserRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoExisteException.class, () -> {
            this.service.listarPorUsuario(0L);
        });
        verify(this.mockUserRepository).findById(any());
    }
}