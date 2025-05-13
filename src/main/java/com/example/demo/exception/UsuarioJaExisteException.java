package com.example.demo.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String s) {
        super(s);
    }
}
