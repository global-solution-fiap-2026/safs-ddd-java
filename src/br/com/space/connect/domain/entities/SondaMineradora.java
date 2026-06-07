package br.com.space.connect.domain.entities;

import br.com.space.connect.domain.valueobjects.*;
import br.com.space.connect.domain.interfaces.Recarregavel;

public class SondaMineradora extends Sonda implements Recarregavel {
    private CompartimentoCarga carga;
    private Double quantidadeExtraida;
    private Recurso recurso;

    public SondaMineradora(String idSonda, NivelEnergia bateria, Coordenada posicaoAtual, Terreno terreno, CompartimentoCarga carga, Double quantidadeExtraida, Recurso recurso){
        super(idSonda, bateria, posicaoAtual, terreno);
        this.carga = carga;
        this.quantidadeExtraida = quantidadeExtraida;
        this.recurso = recurso;
    }

    public CompartimentoCarga getCarga() {
        return carga;
    }

    @Override
    public void conectarBase() {
        this.bateria = new NivelEnergia(bateria.getCapacidadeMaxima(), bateria.getCapacidadeMaxima());
    }

    @Override
    public void realizarAcaoLocal() {
        this.carga = this.carga.adicionar(quantidadeExtraida);

        System.out.println("Mineração concluída! Extraídas " + quantidadeExtraida
                + " unidades. Carga atual: " + carga.getVolumeOcupado()
                + "/" + carga.getVolumeMaximo()
                + "\n/ Recurso extraído: " + recurso.getNome());
    }

    @Override
    public String getStatus() {
        return super.getStatus() +
                " | Carga: " + carga.getVolumeOcupado() +
                "/" + carga.getVolumeMaximo() +
                "\n| Recurso: " + recurso.getNome();
    }
}
