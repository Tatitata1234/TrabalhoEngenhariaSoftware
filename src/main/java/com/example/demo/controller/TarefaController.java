package com.example.demo.controller;

import com.example.demo.controller.request.TarefaRequest;
import com.example.demo.controller.response.TarefaDetalhadoResponse;
import com.example.demo.controller.response.TarefaResponse;
import com.example.demo.exception.TarefaJaExisteException;
import com.example.demo.exception.TarefaNaoExisteException;
import com.example.demo.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TarefaController {

    private static final String ERRO = "Erro :";

    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<TarefaResponse> criar(@RequestBody TarefaRequest request) {
        try {
            TarefaResponse response = service.criar(request);
            return ResponseEntity.ok(response);
        } catch (TarefaJaExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDetalhadoResponse> detalhar(@PathVariable Long id) {
        try {
            TarefaDetalhadoResponse response = service.detalhar(id);
            return ResponseEntity.ok(response);
        } catch (TarefaNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> atualizar(@RequestBody TarefaRequest request, @PathVariable Long id) {
        try {
            TarefaResponse response = service.atualizar(request, id);
            return ResponseEntity.ok(response);
        } catch (TarefaNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TarefaResponse> deletar(@PathVariable Long id) {
        try {
            TarefaResponse response = service.deletar(id);
            return ResponseEntity.ok(response);
        } catch (TarefaNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<TarefaDetalhadoResponse>> listarPorUsuario(@RequestParam Long assignedTo) {
        try {
            List<TarefaDetalhadoResponse> responses = service.listarPorUsuario(assignedTo);
            return ResponseEntity.ok(responses);
        } catch (TarefaNaoExisteException e) {
            System.out.println(ERRO + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
