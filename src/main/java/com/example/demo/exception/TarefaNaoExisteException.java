package com.example.demo.exception;

public class TarefaNaoExisteException extends RuntimeException{
    public TarefaNaoExisteException(String message) {
        super(message);
    }
}
