package br.com.space.connect.infrastructure;

import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.interfaces.SondaRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SondaRepositoryEmMemoria implements SondaRepository {
    private Map<String, Sonda> bancoSondas;

    public SondaRepositoryEmMemoria(){
        this.bancoSondas = new HashMap<>();
    }

    @Override
    public void salvar(Sonda sonda) {
        bancoSondas.put(sonda.getIdSonda(), sonda);
    }

    @Override
    public Sonda buscarPorId(String id) {
        Sonda sonda = bancoSondas.get(id);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada: " + id);
        }
        return sonda;
    }

    @Override
    public Collection<Sonda> listarTodas() {
        return bancoSondas.values();
    }
}
