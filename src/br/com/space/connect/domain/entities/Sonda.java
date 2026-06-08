package br.com.space.connect.domain.entities;

import br.com.space.connect.domain.exception.BateriaCriticaException;
import br.com.space.connect.domain.exception.TerrenoInvalidoException;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.NivelEnergia;
import br.com.space.connect.domain.valueobjects.Terreno;

public abstract class Sonda {
    protected String idSonda;
    protected NivelEnergia bateria;
    protected Coordenada posicaoAtual;
    protected Terreno terreno;

    public Sonda(String idSonda, NivelEnergia bateria, Coordenada posicaoAtual, Terreno terreno){
        if (idSonda == null || idSonda.isBlank()) {
            throw new IllegalArgumentException("ID da sonda não pode ser nulo ou vazio.");
        }
        this.idSonda = idSonda;
        this.bateria = bateria;
        this.posicaoAtual = posicaoAtual;
        this.terreno = terreno;
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
    public void mover(Coordenada destino, Terreno terreno) {
        if (terreno == Terreno.CRATERA) {
            throw new TerrenoInvalidoException("Sonda com rodas não pode entrar em uma Cratera.");
        }
        double custo = Math.abs(destino.getEixoX() - posicaoAtual.getEixoX())
                + Math.abs(destino.getEixoY() - posicaoAtual.getEixoY());
        if (custo > bateria.getCapacidadeAtual()) {
            throw new BateriaCriticaException("Energia insuficiente.");
        }
        this.bateria = this.bateria.consumir(custo);
        this.posicaoAtual = destino;
        this.terreno = terreno;
    }

    //template method
    public void executarRotinaAutonoma(Coordenada destino, Terreno terreno){
        //1-validar status sistema
        System.out.println("Validando sistema... Bateria: "
                + bateria.getCapacidadeAtual() + "/" + bateria.getCapacidadeMaxima());
        //2-mover sonda ao destino
        mover(destino, terreno);
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
