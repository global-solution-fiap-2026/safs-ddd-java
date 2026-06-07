package br.com.space.connect.domain.entities;

import br.com.space.connect.domain.exception.BateriaCriticaException;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.NivelEnergia;

public abstract class Sonda {
    protected String idSonda;
    protected NivelEnergia bateria;
    protected Coordenada posicaoAtual;

    public Sonda(String idSonda, NivelEnergia bateria, Coordenada posicaoAtual){
        if (idSonda == null || idSonda.isBlank()) {
            throw new IllegalArgumentException("ID da sonda não pode ser nulo ou vazio.");
        }
        this.idSonda = idSonda;
        this.bateria = bateria;
        this.posicaoAtual = posicaoAtual;
    }

    public String getIdSonda() {
        return idSonda;
    }

    public Coordenada getPosicaoAtual() {
        return posicaoAtual;
    }

    public NivelEnergia getBateria() {
        return bateria;
    }

    public abstract void realizarAcaoLocal();

    //metodo mover
    public void mover(Coordenada destino) {
        double custoIda = Math.abs(destino.getEixoX() - posicaoAtual.getEixoX()) + Math.abs(destino.getEixoY() - posicaoAtual.getEixoY());
        double custoVolta = Math.abs(destino.getEixoX()) + Math.abs(destino.getEixoY());
        double custoTotal = custoIda + custoVolta;
        if (custoTotal > bateria.getCapacidadeAtual()) {
            throw new BateriaCriticaException("Energia insuficiente para ir ao destino e voltar à base.");
        }
        this.bateria = this.bateria.consumir(custoTotal); // substitui, não altera
        this.posicaoAtual = destino; // substitui, não altera
    }

    //template method
    public void executarRotinaAutonoma(Coordenada destino){
        //1-validar status sistema
        System.out.println("Validando sistema... Bateria: "
                + bateria.getCapacidadeAtual() + "/" + bateria.getCapacidadeMaxima());
        //2-mover sonda ao destino
        mover(destino);
        //3-realizar ação
        realizarAcaoLocal();
        //4-enviar relatorio
        System.out.println("Relatório enviado com sucesso. Sonda: " + getIdSonda()+" na coordenada X: " + posicaoAtual.getEixoX() +" Y: "+ posicaoAtual.getEixoY());
    }

    public String getStatus() {
        return "ID: " + idSonda +
                " | Bateria: " + bateria.getCapacidadeAtual() +
                "/" + bateria.getCapacidadeMaxima() +
                " | Posição: (" + posicaoAtual.getEixoX() +
                ", " + posicaoAtual.getEixoY() + ")";
    }
}
