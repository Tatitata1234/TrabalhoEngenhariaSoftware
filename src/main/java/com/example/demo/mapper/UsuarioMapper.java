package com.example.demo.mapper;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioDetalhadoResponse;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario toEntity(UsuarioRequest request) {
        return new Usuario(request.getNome(), request.getNickname(), request.getSenha(), request.getIdade());
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId());
    }

    public static UsuarioDetalhadoResponse toResponseDetalhado(Usuario usuario) {
        return new UsuarioDetalhadoResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getNickname(),
                usuario.getIdade()
        );
    }
}
