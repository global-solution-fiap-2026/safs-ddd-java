package br.com.space.connect.domain.entities;

import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.NivelEnergia;
import br.com.space.connect.domain.interfaces.Recarregavel;

public class SondaExploradora extends Sonda implements Recarregavel {
    private Double alcanceSensor;

    public SondaExploradora(String idSonda, NivelEnergia bateria, Coordenada posicaoAtual, Double alcanceSensor){
        super(idSonda, bateria, posicaoAtual);
        if(alcanceSensor <= 0){
            throw new IllegalArgumentException("Alcance do sensor deve ser maior que zero");
        }
        this.alcanceSensor = alcanceSensor;
    }

    public Double getAlcanceSensor() {
        return alcanceSensor;
    }

    @Override
    public void realizarAcaoLocal() {
        System.out.println("Sonda " + idSonda + " mapeando área na posição " +
                "(" + posicaoAtual.getEixoX() + ", " + posicaoAtual.getEixoY() +
                ") com alcance de " + alcanceSensor + " unidades.");
    }

    @Override
    public void conectarBase() {
        bateria = new NivelEnergia(bateria.getCapacidadeMaxima(), bateria.getCapacidadeMaxima());
    }
}
