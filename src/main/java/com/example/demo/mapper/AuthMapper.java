package com.example.demo.mapper;

import com.example.demo.controller.response.AuthResponse;

public class AuthMapper {

    public static AuthResponse toResponse(String token) {
        StringBuilder tokenBearer = new StringBuilder("Bearer ");
        tokenBearer.append(token);
        return new AuthResponse(tokenBearer.toString());
    }
}
