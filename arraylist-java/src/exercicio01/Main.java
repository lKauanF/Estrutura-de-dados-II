package exercicio01;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Cliente> clientes = new ArrayList<>();

        while (true) {
            System.out.print("Digite o ID do cliente ou um número negativo para sair: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id < 0) {
                break;
            }

            System.out.print("Digite o nome do cliente: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a idade do cliente: ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Digite o telefone do cliente: ");
            String telefone = scanner.nextLine();

            Cliente cliente = new Cliente(id, nome, idade, telefone);
            clientes.add(cliente);

            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println();
        }

        System.out.println();
        System.out.println("Clientes cadastrados:");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente foi cadastrado.");
        } else {
            for (Cliente cliente : clientes) {
                cliente.exibirDados();
            }
        }

        scanner.close();
    }
}