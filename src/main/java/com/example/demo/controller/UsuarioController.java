package com.example.demo.controller;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.UsuarioJaExisteException;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest request) {
        try {
            UsuarioResponse response = service.criar(request);
            return ResponseEntity.ok(response);
        } catch (UsuarioJaExisteException e) {
            System.out.println("Erro :" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
