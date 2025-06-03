package com.example.demo.service;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioDetalhadoResponse;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.UsuarioJaExisteException;
import com.example.demo.exception.UsuarioNaoExisteException;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private static final String USUARIO_NAO_EXISTE = "Usuário não existe!";
    @Autowired
    private UsuarioRepository repository;

    public UsuarioResponse criar(UsuarioRequest request) {
        Usuario jaExiste = repository.findByNickname(request.getNickname());

        if (jaExiste != null) {
            throw new UsuarioJaExisteException("Usuário já existe!");
        }

        Usuario usuario = UsuarioMapper.toEntity(request);
        repository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioDetalhadoResponse detalhar(Long id) {
        Optional<Usuario> usuario = repository.findById(id);

        if (usuario.isEmpty()) {
            throw new UsuarioNaoExisteException(USUARIO_NAO_EXISTE);
        }

        return UsuarioMapper.toResponseDetalhado(usuario.get());
    }

    public UsuarioResponse atualizar(UsuarioRequest request, Long id) {
        Optional<Usuario> usuarioOp = repository.findById(id);

        if (usuarioOp.isEmpty()) {
            throw new UsuarioNaoExisteException(USUARIO_NAO_EXISTE);
        }

        Usuario usuario = usuarioOp.get();

        usuario.setIdade(request.getIdade());
        usuario.setNickname(request.getNickname());
        usuario.setNome(request.getNome());
        usuario.setSenha(request.getSenha());

        this.repository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse deletar(Long id) {
        Optional<Usuario> usuarioOp = repository.findById(id);

        if (usuarioOp.isEmpty()) {
            throw new UsuarioNaoExisteException(USUARIO_NAO_EXISTE);
        }

        Usuario usuario = usuarioOp.get();

        usuario.setAtivo(false);

        repository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsuarioNaoExisteException {
        Usuario usuario = repository.findByNickname(username);

        if (usuario == null) {
            throw new UsuarioNaoExisteException("Usuário não encontrado: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getNome(),
                usuario.getSenha(),
                Collections.singleton(usuario::getNickname)
        );
    }
}
