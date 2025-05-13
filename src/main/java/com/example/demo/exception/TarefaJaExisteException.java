package com.example.demo.exception;

public class TarefaJaExisteException extends RuntimeException {
    public TarefaJaExisteException(String message) {
        super(message);
    }
}
