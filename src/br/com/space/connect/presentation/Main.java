package br.com.space.connect.presentation;

import br.com.space.connect.application.MissaoService;
import br.com.space.connect.domain.entities.Sonda;
import br.com.space.connect.domain.exception.BateriaCriticaException;
import br.com.space.connect.domain.exception.CargaExcedidaException;
import br.com.space.connect.domain.exception.TerrenoInvalidoException;
import br.com.space.connect.domain.interfaces.SondaRepository;
import br.com.space.connect.domain.valueobjects.Coordenada;
import br.com.space.connect.domain.valueobjects.Recurso;
import br.com.space.connect.domain.valueobjects.Terreno;
import br.com.space.connect.infrastructure.SondaRepositoryEmMemoria;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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

            String entrada = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Opção inválida. Digite um número entre 1 e 5.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    try {
                        System.out.println("Tipo de sonda (MINERACAO / EXPLORACAO): ");
                        String tipo = scanner.nextLine().trim();

                        System.out.println("Capacidade máxima de bateria: ");
                        double capacidadeMaximaBateria = Double.parseDouble(scanner.nextLine().trim());

                        Recurso recurso = null;
                        double capacidadeMaximaCarga = 0;
                        double alcanceSensor = 0;

                        if (tipo.equalsIgnoreCase("MINERACAO")) {
                            System.out.println("Tipo de recurso (GELO / REGOLITO / TITANIO): ");
                            recurso = Recurso.valueOf(scanner.nextLine().trim().toUpperCase());
                            System.out.println("Capacidade máxima de carga: ");
                            capacidadeMaximaCarga = Double.parseDouble(scanner.nextLine().trim());
                        } else if (tipo.equalsIgnoreCase("EXPLORACAO")) {
                            System.out.println("Alcance do sensor em metros: ");
                            alcanceSensor = Double.parseDouble(scanner.nextLine().trim());
                        }

                        System.out.println("Terreno inicial: PLANICIE");
                        Sonda sonda = missaoService.lancarSonda(tipo, recurso, capacidadeMaximaBateria, capacidadeMaximaCarga, alcanceSensor);
                        System.out.println("✅ Sonda lançada com sucesso: " + sonda.getIdSonda());
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Erro: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Listando as sondas ativas: ");
                    missaoService.listarStatusSondas().forEach(System.out::println);
                }
                case 3 -> {
                    try {
                        System.out.println("Digite o ID da sonda que deseja enviar: ");
                        String idSonda = scanner.nextLine().trim();

                        missaoService.buscarSonda(idSonda);

                        System.out.println("X: ");
                        int eixoX = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println("Y: ");
                        int eixoY = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println("Tipo de terreno (PLANICIE / SOLO_ROCHOSO / CRATERA): ");
                        Terreno terreno = Terreno.valueOf(scanner.nextLine().trim().toUpperCase());

                        Coordenada destino = new Coordenada(eixoX, eixoY);
                        missaoService.enviarSonda(idSonda, destino, terreno);
                        System.out.println("✅ Missão executada com sucesso!");
                        System.out.println(missaoService.getStatusSonda(idSonda));
                    } catch (BateriaCriticaException e) {
                        System.out.println("⚠ Alerta: Bateria Crítica! " + e.getMessage());
                    } catch (TerrenoInvalidoException e) {
                        System.out.println("⚠ Alerta: Terreno Inválido! " + e.getMessage());
                    } catch (CargaExcedidaException e) {
                        System.out.println("⚠ Alerta: Carga Excedida! " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Erro: Digite um valor numérico válido para as coordenadas.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Erro: " + e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        System.out.println("Digite o ID da sonda que deseja conectar na base: ");
                        String idSonda = scanner.nextLine().trim();
                        missaoService.recarregarSonda(idSonda);
                        System.out.println("✅ Sonda conectada à base e bateria recarregada.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Erro: " + e.getMessage());
                    } catch (BateriaCriticaException e){
                        System.out.println("⚠ Alerta: Bateria Crítica! " + e.getMessage());
                    }
                }
                case 5 -> System.out.println("Encerrando missão...");
                default -> System.out.println("⚠ Opção inválida. Digite um número entre 1 e 5.");
            }
        }

        scanner.close();
    }
}