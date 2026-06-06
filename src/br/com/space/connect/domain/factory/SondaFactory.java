package br.com.space.connect.domain.factory;

import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.entities.SondaExploradora;
import br.com.space.connect.domain.entities.SondaMineradora;
import br.com.space.connect.domain.valueobjects.CompartimentoCarga;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.NivelEnergia;

public class SondaFactory {
    private static int contador = 1;

    public static Sonda criarSonda(String tipoSonda){
        if(tipoSonda.equalsIgnoreCase("MINERACAO")){
            NivelEnergia bateria = new NivelEnergia(100.0, 100.0);
            Coordenada posicao = new Coordenada(0, 0);
            CompartimentoCarga carga = new CompartimentoCarga(0.0, 50.0);
            return new SondaMineradora("SND-" + String.format("%03d", contador++), bateria, posicao, carga, 10.0);
        }
        else if(tipoSonda.equalsIgnoreCase("EXPLORACAO")){
            NivelEnergia bateria = new NivelEnergia(100.0, 100.0);
            Coordenada posicao = new Coordenada(0, 0);
            Double alcanceSensor = 16.5;
            return new SondaExploradora("SND-" +String.format("%03d", contador++), bateria, posicao, alcanceSensor);
        }
        else{
            throw new IllegalArgumentException("Tipo de sonda inexistente");
        }
    }
}
