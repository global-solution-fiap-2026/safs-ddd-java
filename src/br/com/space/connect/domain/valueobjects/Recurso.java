package br.com.space.connect.domain.valueobjects;

public enum Recurso {
    GELO("Gelo", 0.5),
    REGOLITO("Regolito", 1.0),
    TITANIO("Titanio", 2.0);

    private final String nome;
    private final Double pesoPorUnidade;

    Recurso(String nome, Double pesoPorUnidade){
        this.nome = nome;
        this.pesoPorUnidade = pesoPorUnidade;
    }

    public String getNome() {
        return nome;
    }

    public Double getPesoPorUnidade() {
        return pesoPorUnidade;
    }
}
