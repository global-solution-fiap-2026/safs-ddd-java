package br.com.space.connect.domain.valueobjects;

import br.com.space.connect.domain.exception.BateriaCriticaException;

public final class NivelEnergia {
    private final Double capacidadeAtual;
    private final Double capacidadeMaxima;

    public NivelEnergia(Double capacidadeAtual, Double capacidadeMaxima){
        if(capacidadeMaxima < 0 || capacidadeAtual < 0 ){
            throw new BateriaCriticaException("Bateria nnão pode ter cargas negativas.");
        }
        if(capacidadeAtual > capacidadeMaxima){
            throw new BateriaCriticaException("Bateria em estado crítico, capacidade máxima atingida.");
        }
        this.capacidadeAtual = capacidadeAtual;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Double getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public Double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public NivelEnergia consumir(Double quantidade){
        if(quantidade > capacidadeAtual){
            throw new BateriaCriticaException("Energia insuficiente");
        }
        return new NivelEnergia(capacidadeAtual - quantidade, capacidadeMaxima);
    }
}
