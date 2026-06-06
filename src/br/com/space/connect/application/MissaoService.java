package br.com.space.connect.application;

import br.com.space.connect.domain.entities.CentroDeComando;
import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.factory.SondaFactory;
import br.com.space.connect.domain.interfaces.SondaRepository;
import br.com.space.connect.domain.valueobjects.Coordenada;

import java.util.Collection;

public class MissaoService {
    private final SondaRepository repository;
    private final CentroDeComando centroDeComando;

    public MissaoService(SondaRepository repository) {
        this.repository = repository;
        this.centroDeComando = CentroDeComando.getInstancia();
    }

    public Sonda lancarSonda(String tipo){
        Sonda sonda = SondaFactory.criarSonda(tipo);
        repository.salvar(sonda);
        centroDeComando.registrarSonda(sonda);
        return sonda;
    }

    public Collection<Sonda> listarSondas(){
        return repository.listarTodas();
    }

    public Sonda buscarSonda(String id){
        return repository.buscarPorId(id);
    }

    public void enviarSonda(String id, Coordenada destino){
        Sonda sonda = repository.buscarPorId(id);
        sonda.executarRotinaAutonoma(destino);
    }


}
