package br.com.space.connect.domain.exception;

public class BateriaCriticaException extends RuntimeException{
    public BateriaCriticaException (String mensagem){
        super(mensagem);
    }
}
