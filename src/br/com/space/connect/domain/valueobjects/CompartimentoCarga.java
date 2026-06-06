package br.com.space.connect.domain.valueobjects;

import br.com.space.connect.domain.exception.CargaExcedidaException;

public final class CompartimentoCarga {
    private final Double volumeOcupado;
    private final Double volumeMaximo;

    public CompartimentoCarga(Double volumeOcupado, Double volumeMaximo){
        if(volumeOcupado < 0){
            throw new CargaExcedidaException("O volume ocupado da sonda não pode menor que zero.");
        }
        if(volumeMaximo < 0){
            throw new CargaExcedidaException("O volume máximo da sonda não pode ser menor que zero.");
        }
        if(volumeOcupado > volumeMaximo){
            throw new CargaExcedidaException("A carga da sonda não pode ser maior que o máximo suportado por ela.");
        }
        this.volumeMaximo = volumeMaximo;
        this.volumeOcupado = volumeOcupado;
    }

    public Double getVolumeMaximo() {
        return volumeMaximo;
    }

    public Double getVolumeOcupado() {
        return volumeOcupado;
    }

    public CompartimentoCarga adicionar(Double volume){
        if(volumeOcupado + volume > volumeMaximo){
            throw new CargaExcedidaException("O volume adicionado excede a carga máxima!");
        }
        return new CompartimentoCarga(volumeOcupado + volume, volumeMaximo);
    }
}
