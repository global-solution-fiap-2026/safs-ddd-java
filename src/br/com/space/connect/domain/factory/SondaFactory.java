package br.com.space.connect.domain.factory;

import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.entities.SondaExploradora;
import br.com.space.connect.domain.entities.SondaMineradora;
import br.com.space.connect.domain.valueobjects.*;

public class SondaFactory {
    private static int contador = 1;

    public static Sonda criarSondaMineradora(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException("Recurso não pode ser nulo para uma Sonda Mineradora.");
        }
        NivelEnergia bateria = new NivelEnergia(100.0, 100.0);
        Coordenada posicao = new Coordenada(0, 0);
        CompartimentoCarga carga = new CompartimentoCarga(0.0, 50.0);
        return new SondaMineradora(
                "SND-" + String.format("%03d", contador++),
                bateria, posicao, Terreno.PLANICIE, carga, 10.0, recurso
        );
    }

    public static Sonda criarSondaExploradora() {
        NivelEnergia bateria = new NivelEnergia(100.0, 100.0);
        Coordenada posicao = new Coordenada(0, 0);
        return new SondaExploradora(
                "SND-" + String.format("%03d", contador++),
                bateria, posicao, Terreno.PLANICIE, 16.5
        );
    }
}
