package br.com.space.connect.application;

import br.com.space.connect.domain.entities.CentroDeComando;
import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.factory.SondaFactory;
import br.com.space.connect.domain.interfaces.Recarregavel;
import br.com.space.connect.domain.interfaces.SondaRepository;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.Recurso;
import br.com.space.connect.domain.valueobjects.Terreno;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MissaoService {
    private final SondaRepository repository;
    private final CentroDeComando centroDeComando;

    public MissaoService(SondaRepository repository) {
        this.repository = repository;
        this.centroDeComando = CentroDeComando.getInstancia();
    }

    public Sonda lancarSonda(String tipo, Recurso recurso,  double capacidadeMaximaBateria, double capacidadeMaximaCarga, double alcanceSensor) {
        Sonda sonda;
        if (tipo.equalsIgnoreCase("MINERACAO")) {
            sonda = SondaFactory.criarSondaMineradora(recurso, capacidadeMaximaBateria, capacidadeMaximaCarga);
        } else if (tipo.equalsIgnoreCase("EXPLORACAO")) {
            sonda = SondaFactory.criarSondaExploradora(capacidadeMaximaBateria, alcanceSensor);
        } else {
            throw new IllegalArgumentException("Tipo de sonda inexistente.");
        }
        repository.salvar(sonda);
        centroDeComando.registrarSonda(sonda);
        return sonda;
    }

    public Sonda buscarSonda(String id){
        return repository.buscarPorId(id);
    }

    public void enviarSonda(String id, Coordenada destino, Terreno terreno){
        Sonda sonda = repository.buscarPorId(id);
        sonda.executarRotinaAutonoma(destino, terreno);
        sonda.getStatus();
    }

    public void recarregarSonda(String id) {
        Sonda sonda = repository.buscarPorId(id);
        Coordenada base = new Coordenada(0,0);
        sonda.mover(base, Terreno.PLANICIE);
        if (sonda instanceof Recarregavel) {
            ((Recarregavel) sonda).conectarBase();
        } else {
            throw new IllegalArgumentException("Essa sonda não suporta recarga.");
        }
    }

    public String getStatusSonda(String id) {
        return repository.buscarPorId(id).getStatus();
    }

    public List<String> listarStatusSondas() {
        return repository.listarTodas().stream()
                .map(Sonda::getStatus)
                .collect(Collectors.toList());
    }


}
