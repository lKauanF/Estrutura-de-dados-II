package exercicio05;

import java.util.ArrayList;
import java.util.Scanner;

public class MAn {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();

        int option;

        do {
            showMenu();

            System.out.print("Opção desejada: ");
            option = scanner.nextInt();
            scanner.nextLine();

            System.out.println();

            switch (option) {
                case 1:
                    includeContact(scanner, agenda);
                    break;

                case 2:
                    deleteContact(scanner, agenda);
                    break;

                case 3:
                    agenda.list();
                    break;

                case 4:
                    searchContact(scanner, agenda);
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            System.out.println();

        } while (option != 0);

        scanner.close();
    }

    public static void showMenu() {
        System.out.println(" Menu Principal ");
        System.out.println("[ 1 ] Incluir Contato");
        System.out.println("[ 2 ] Excluir Contato");
        System.out.println("[ 3 ] Listar Contatos");
        System.out.println("[ 4 ] Pesquisar Contato");
        System.out.println("[ 0 ] Encerrar o Programa");
    }

    public static void includeContact(Scanner scanner, Agenda agenda) {
        System.out.println("[ 1 ] Incluir Contato");

        System.out.print("Informe o nome do contato: ");
        String name = scanner.nextLine();

        System.out.print("Informe o e-mail do contato: ");
        String email = scanner.nextLine();

        System.out.print("Informe o telefone do contato: ");
        String phone = scanner.nextLine();

        Contato contact = new Contato(name, email, phone);
        agenda.include(contact);

        System.out.println("Contato incluído com sucesso.");
    }

    public static void deleteContact(Scanner scanner, Agenda agenda) {
        System.out.println("[ 2 ] Excluir Contato");

        if (agenda.isEmpty()) {
            System.out.println("Nenhum contato cadastrado para excluir.");
            return;
        }

        agenda.list();

        System.out.print("Informe o nome do contato que deseja excluir: ");
        String name = scanner.nextLine();

        boolean deleted = agenda.deleteByName(name);

        if (deleted) {
            System.out.println("Contato excluído com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    public static void searchContact(Scanner scanner, Agenda agenda) {
        System.out.println("[ 4 ] Pesquisar Contato");

        if (agenda.isEmpty()) {
            System.out.println("Nenhum contato cadastrado para pesquisar.");
            return;
        }

        System.out.print("Informe uma parte do nome do contato: ");
        String namePart = scanner.nextLine();

        ArrayList<Contato> foundContacts = agenda.searchByNamePart(namePart);

        if (foundContacts.isEmpty()) {
            System.out.println("Nenhum contato encontrado.");
            return;
        }

        System.out.println("Contatos encontrados:");

        for (Contato contact : foundContacts) {
            System.out.println("--");
            contact.showData();
        }

        System.out.println("--");
    }
}