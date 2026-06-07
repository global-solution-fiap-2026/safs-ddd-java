package br.com.space.connect.presentation;

import br.com.space.connect.application.MissaoService;
import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.exception.BateriaCriticaException;
import br.com.space.connect.domain.interfaces.SondaRepository;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.infrastructure.SondaRepositoryEmMemoria;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        SondaRepository repository = new SondaRepositoryEmMemoria();
        MissaoService missaoService = new MissaoService(repository);

        int opcao = 0;

        while (opcao != 5) {
            System.out.println("\n=== CENTRO DE COMANDO ===");
            System.out.println("1 - Lançar nova sonda");
            System.out.println("2 - Listar sondas");
            System.out.println("3 - Enviar sonda para coordenada e realizar ação");
            System.out.println("4 - Recarregar sonda");
            System.out.println("5 - Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.println("Tipo de sonda (MINERACAO / EXPLORACAO): ");
                    String tipo = scanner.next();
                    try {
                        Sonda sonda = missaoService.lancarSonda(tipo);
                        System.out.println("Sonda lançada com sucesso: " + sonda.getIdSonda());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Listando as sondas ativas: ");
                    missaoService.listarSondas()
                            .forEach(sonda -> System.out.println(
                                    "ID: " + sonda.getIdSonda() +
                                            " | Bateria: " + sonda.getBateria().getCapacidadeAtual() +
                                            " | Posição: (" + sonda.getPosicaoAtual().getEixoX() +
                                            ", " + sonda.getPosicaoAtual().getEixoY() + ")"
                            ));
                }
                case 3 -> {
                    System.out.println("Digite o ID da sonda que deseja enviar: ");
                    String idSonda = scanner.next();

                    try {
                        missaoService.buscarSonda(idSonda); // valida se existe antes de continuar
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Erro: " + e.getMessage());
                        break;
                    }

                    System.out.println("X: ");
                    int eixoX = scanner.nextInt();
                    System.out.println("Y: ");
                    int eixoY = scanner.nextInt();

                    try {
                        Coordenada destino = new Coordenada(eixoX, eixoY);
                        missaoService.enviarSonda(idSonda, destino);
                        System.out.println("Missão executada com sucesso!");
                    } catch (BateriaCriticaException e) {
                        System.out.println("⚠ Alerta: Bateria Crítica! " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Erro: " + e.getMessage());
                    }
                }
                case 4 ->{
                    System.out.println("Digite o id da sonda que deseja conectar na base");
                    String sonda = scanner.next();
                    try{
                        missaoService.recarregarSonda(sonda);
                        System.out.println("Sonda conectada à base e bateria recarregada.");
                    }catch (IllegalArgumentException e){
                        System.out.println("⚠ Erro: " + e.getMessage());
                    }
                }
                case 5 -> System.out.println("Encerrando missão...");
            }
        }
    }
}