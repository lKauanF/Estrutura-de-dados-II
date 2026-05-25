package exercicio02;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> telefones = new ArrayList<>();

        String opcao;

        do {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("(a) adicionar telefone");
            System.out.println("(r) remover telefone");
            System.out.println("(l) listar telefones");
            System.out.println("(s) sair");
            System.out.print("Opção: ");

            opcao = scanner.nextLine().toLowerCase();

            switch (opcao) {
                case "a":
                    System.out.print("Digite o telefone: ");
                    String telefone = scanner.nextLine();

                    telefones.add(telefone);

                    System.out.println("Telefone adicionado com sucesso!");
                    break;

                case "r":
                    if (telefones.isEmpty()) {
                        System.out.println("Nenhum telefone cadastrado para remover.");
                    } else {
                        listarTelefones(telefones);

                        System.out.print("Digite a posição do telefone que deseja remover: ");
                        int posicao = scanner.nextInt();
                        scanner.nextLine();

                        if (posicao >= 0 && posicao < telefones.size()) {
                            String telefoneRemovido = telefones.remove(posicao);
                            System.out.println("Telefone removido: " + telefoneRemovido);
                        } else {
                            System.out.println("Posição inválida.");
                        }
                    }
                    break;

                case "l":
                    listarTelefones(telefones);
                    break;

                case "s":
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (!opcao.equals("s"));

        scanner.close();
    }

    public static void listarTelefones(ArrayList<String> telefones) {
        if (telefones.isEmpty()) {
            System.out.println("Nenhum telefone cadastrado.");
            return;
        }

        System.out.println();
        System.out.println("Telefones cadastrados:");

        for (int i = 0; i < telefones.size(); i++) {
            System.out.println("Posição " + i + ": " + telefones.get(i));
        }
    }
}