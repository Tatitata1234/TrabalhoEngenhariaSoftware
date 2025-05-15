package com.example.demo.controller;

import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.UsuarioDetalhadoResponse;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.exception.UsuarioJaExisteException;
import com.example.demo.exception.UsuarioNaoExisteException;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuario", description = "API de Usuario")
public class UsuarioController {
    private static final String ERRO = "Erro :";
    @Autowired
    private UsuarioService service;

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um usuário e retorna o id")
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest request) {
        try {
            UsuarioResponse response = service.criar(request);
            return ResponseEntity.ok(response);
        } catch (UsuarioJaExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém usuário", description = "Busca um usuário pelo id")
    public ResponseEntity<UsuarioDetalhadoResponse> detalhar(@PathVariable Long id) {
        try {
            UsuarioDetalhadoResponse response = service.detalhar(id);
            return ResponseEntity.ok(response);
        } catch (UsuarioNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza usuário", description = "Atualiza um usuário pelo id e request")
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody UsuarioRequest request, @PathVariable Long id) {
        try {
            UsuarioResponse response = service.atualizar(request, id);
            return ResponseEntity.ok(response);
        } catch (UsuarioNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta usuário", description = "Deleta um usuário pelo id")
    public ResponseEntity<UsuarioResponse> deletar(@PathVariable Long id) {
        try {
            UsuarioResponse response = service.deletar(id);
            return ResponseEntity.ok(response);
        } catch (UsuarioNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
