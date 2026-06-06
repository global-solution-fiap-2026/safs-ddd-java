package br.com.space.connect.domain.entities;

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
        double custo = Math.abs(destino.getEixoX() - posicaoAtual.getEixoX()) + Math.abs(destino.getEixoY() - posicaoAtual.getEixoY());
        this.bateria = this.bateria.consumir(custo); // substitui, não altera
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
        System.out.println("Relatório enviado com sucesso. Sonda:" + getIdSonda()+"na coordenada" + posicaoAtual.getEixoX() + posicaoAtual.getEixoY());
    }
}
