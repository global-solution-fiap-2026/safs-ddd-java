package br.com.space.connect.domain.exception;

public class CargaExcedidaException extends RuntimeException {
    public CargaExcedidaException(String mensagem){
        super(mensagem);
    }
}
