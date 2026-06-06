package br.com.space.connect.domain.entities;

import br.com.space.connect.domain.valueobjects.CompartimentoCarga;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.NivelEnergia;
import br.com.space.connect.domain.interfaces.Recarregavel;

public class SondaMineradora extends Sonda implements Recarregavel {
    private CompartimentoCarga carga;
    private Double quantidadeExtraida;

    public SondaMineradora(String idSonda, NivelEnergia bateria, Coordenada posicaoAtual, CompartimentoCarga carga, Double quantidadeExtraida){
        super(idSonda, bateria, posicaoAtual);
        this.carga = carga;
        this.quantidadeExtraida = quantidadeExtraida;
    }

    public CompartimentoCarga getCarga() {
        return carga;
    }

    @Override
    public void conectarBase() {
        this.bateria = new NivelEnergia(bateria.getCapacidadeMaxima(), bateria.getCapacidadeMaxima());
    }

    @Override
    public void realizarAcaoLocal(){
        this.carga = this.carga.adicionar(quantidadeExtraida);
    }
}
