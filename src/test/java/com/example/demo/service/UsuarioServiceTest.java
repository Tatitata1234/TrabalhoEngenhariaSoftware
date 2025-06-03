package com.example.demo.service;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioDetalhadoResponse;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Tarefa;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.UsuarioJaExisteException;
import com.example.demo.exception.UsuarioNaoExisteException;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository mockRepository;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarHappyPath() {
        when(this.mockRepository.findByNickname(any())).thenReturn(null);
        when(this.mockRepository.save(any())).then(new Answer<Usuario>() {
            @Override
            public Usuario answer(InvocationOnMock invocationOnMock) throws Throwable {
                Usuario arg = invocationOnMock.getArgument(0);
                arg.setId(1L);
                return null;
            }
        });

        UsuarioRequest req = new UsuarioRequest("a", "a", "a", 20);
        UsuarioResponse resp = this.service.criar(req);

        assertEquals(1L, resp.getId());
        verify(this.mockRepository).findByNickname(any());
        verify(this.mockRepository).save(any());
    }

    @Test
    void criarUserExist() {
        Usuario user = new Usuario();
        when(this.mockRepository.findByNickname(any())).thenReturn(user);

        UsuarioRequest req = new UsuarioRequest("a", "a", "a", 20);

        assertThrows(UsuarioJaExisteException.class, () -> { this.service.criar(req); });
        verify(this.mockRepository).findByNickname(any());
    }

    @Test
    void detalharHappyPath() {
        Usuario user = new Usuario("a", "a", "a", 20);
        when(this.mockRepository.findById(any())).thenReturn(Optional.of(user));

        UsuarioDetalhadoResponse resp = this.service.detalhar(0L);

        verify(this.mockRepository).findById(any());
        assertEquals(resp.getNickname(), user.getNickname());
        assertEquals(resp.getIdade(), user.getIdade());
        assertEquals(resp.getNome(), user.getNome());
    }

    @Test
    void detalharUserIsEmpty() {
        when(this.mockRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoExisteException.class, () -> { this.service.detalhar(0L); });
        verify(this.mockRepository).findById(any());
    }

    @Test
    void atualizarHappyPath() {
        Usuario user = new Usuario("a", "a", "a", 20);
        when(this.mockRepository.findById(any())).thenReturn(Optional.of(user));

        UsuarioRequest req = new UsuarioRequest("a", "a", "a", 21);
        this.service.atualizar(req, 0L);
        verify(this.mockRepository).findById(any());
        verify(this.mockRepository).save(any());
    }

    @Test
    void atualizarUserIsEmpty() {
        when(this.mockRepository.findById(any())).thenReturn(Optional.empty());

        UsuarioRequest req = new UsuarioRequest("a", "a", "a", 21);
        assertThrows(UsuarioNaoExisteException.class, () -> { this.service.atualizar(req, 0L); });
        verify(this.mockRepository).findById(any());
    }

    @Test
    void deletarHappyPath() {
        Usuario user = new Usuario("a", "a", "a", 20);
        when(this.mockRepository.findById(any())).thenReturn(Optional.of(user));

        this.service.deletar(0L);
        verify(this.mockRepository).findById(any());
        verify(this.mockRepository).save(any());
    }

    @Test
    void deletarUserIsEmpty() {
        when(this.mockRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoExisteException.class, () ->{ this.service.deletar(0L); });
        verify(this.mockRepository).findById(any());
    }
}