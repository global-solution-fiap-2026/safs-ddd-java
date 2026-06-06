package br.com.space.connect.domain.interfaces;

import br.com.space.connect.domain.entities.Sonda;

import java.util.Collection;

public interface SondaRepository {
    void salvar(Sonda sonda);
    Sonda buscarPorId(String id);
    Collection<Sonda> listarTodas();
}
