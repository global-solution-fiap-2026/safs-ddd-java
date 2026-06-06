package br.com.space.connect.domain.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CentroDeComando {
    private static CentroDeComando instancia;
    private Map<String, Sonda> sondas;

    private CentroDeComando(){
        this.sondas = new HashMap<>();
    }

    public static CentroDeComando getInstancia() {
        if (instancia == null) {
            instancia = new CentroDeComando();
        }
        return instancia;
    }

    public void registrarSonda(Sonda sonda){
        if(sondas.containsKey(sonda.getIdSonda())){
            throw new IllegalArgumentException("Já existe uma sonda com esse ID");
        }
        sondas.put(sonda.getIdSonda(), sonda);
    }

    public Collection<Sonda> listarSondas() {
        return sondas.values();
    }

    public Sonda buscarSonda(String id) {
        Sonda sonda = sondas.get(id);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada: " + id);
        }
        return sonda;
    }
}
