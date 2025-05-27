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
        when(this.mockTaskRepository.save(any())).then(new Answer<Tarefa>() {
            public Tarefa answer(InvocationOnMock invocation) {
                Tarefa arg = invocation.getArgument(0);
                arg.setId(0L);
                return null;
            }
        });

        TarefaResponse resp = this.service.criar(
                new TarefaRequest(
                        "a",
                        "a",
                        "a",
                        0L
                )
        );
        TarefaResponse expected = new TarefaResponse(0L);
        assertEquals(expected.getId(), resp.getId());
    }

    @Test
    void criarTaskExists() {
        Usuario user = new Usuario("test", "test", "test", 20);

        when(this.mockTaskRepository.findByTitulo(any())).thenReturn(new Tarefa());

        assertThrows(TarefaJaExisteException.class, () -> {
            TarefaResponse response = this.service.criar(
                    new TarefaRequest(
                            "a",
                            "a",
                            "a",
                            0L
                    )
            );
        });
    }

    @Test
    void criarUserIsEmpty() {
        Usuario user = new Usuario("test", "test", "test", 20);

        when(this.mockTaskRepository.findByTitulo(any())).thenReturn(null);
        when(this.mockUserRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoExisteException.class, () -> {
            this.service.criar(
                    new TarefaRequest(
                            "a",
                            "a",
                            "a",
                            0L
                    )
            );
        });
    }

    @Test
    void detalharNotEmpty() {
        Tarefa task = new Tarefa("a", "a", "a");
        task.setId(0L);
        when(mockTaskRepository.findById(any())).thenReturn(Optional.of(task));

        TarefaDetalhadoResponse resp = this.service.detalhar(0L);
        assertEquals(task.getId(), resp.getId());
    }

    @Test
    void detalharEmpty() {
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoExisteException.class, () -> {
            this.service.detalhar(0L);
        });
    }

    @Test
    void atualizarHappyPath() {
        Tarefa task = new Tarefa("a", "a", "a");
        task.setId(0L);
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.of(task));

        TarefaRequest req = new TarefaRequest("b", "b", "b", 0L);

        TarefaDetalhadoResponse resp = this.service.atualizar(req, 0L);
        verify(this.mockTaskRepository, times(1)).save(any());

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
    }

    @Test
    void deletarHappyPath() {
        Tarefa task = new Tarefa("a", "a", "a");

        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.of(task));
        when(this.mockTaskRepository.save(any())).then(new Answer<Tarefa>() {
            public Tarefa answer(InvocationOnMock invocation) {
                Tarefa arg = invocation.getArgument(0);
                assertFalse(arg.isAtivo());
                return null;
            }
        });

        this.service.deletar(0L);
    }

    @Test
    void deletarTaskIsEmpty() {
        when(this.mockTaskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoExisteException.class, () -> {
            this.service.deletar(0L);
        });
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
    }
    @Test
    void listarPorUsuarioUserIsEmpty() {
        when(this.mockUserRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoExisteException.class, () -> {
            this.service.listarPorUsuario(0L);
        });
    }
}