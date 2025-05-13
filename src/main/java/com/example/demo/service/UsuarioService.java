package com.example.demo.service;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.UsuarioJaExisteException;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

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
}
