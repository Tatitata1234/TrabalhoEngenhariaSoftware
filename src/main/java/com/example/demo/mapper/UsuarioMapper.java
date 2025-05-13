package com.example.demo.mapper;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Usuario;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioRequest request) {
        return new Usuario(request.getNome(), request.getNickname(), request.getSenha(), request.getIdade());
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return null;
    }
}
