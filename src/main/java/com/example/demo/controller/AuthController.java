package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.controller.request.UsuarioRequest;
import com.example.demo.controller.response.AuthResponse;
import com.example.demo.controller.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.mapper.AuthMapper;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Faz o login do usuário")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioRequest usuarioRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioRequest.getNickname(),
                        usuarioRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(usuarioRequest.getNickname());
        AuthResponse response = AuthMapper.toResponse(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registra usuário", description = "Faz o registro do usuário")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest request) {
        request.setSenha(passwordEncoder.encode(request.getSenha()));
        UsuarioResponse response = usuarioService.criar(request);
        return ResponseEntity.ok(response);
    }
}

