package br.com.space.connect.domain.valueobjects;

public final class Coordenada {
    private final Integer eixoX;
    private final Integer eixoY;

    public Coordenada(Integer eixoX, Integer eixoY){
        if(eixoX < 0 || eixoY < 0){
            throw new IllegalArgumentException("Coordenada inválida");
        }
        this.eixoX = eixoX;
        this.eixoY = eixoY;
    }

    //getters
    public Integer getEixoX() {
        return eixoX;
    }

    public Integer getEixoY() {
        return eixoY;
    }
}
