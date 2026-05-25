package exercicio04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Gerador de números aleatórios distintos");
        System.out.println();

        int quantity = readValidQuantity(scanner);

        while (numbers.size() < quantity) {
            int randomNumber = random.nextInt(100) + 1;

            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }

        System.out.println();
        System.out.println("ArrayList na ordem original:");
        System.out.println(numbers);

        ArrayList<Integer> ascendingNumbers = new ArrayList<>(numbers);
        Collections.sort(ascendingNumbers);

        System.out.println();
        System.out.println("ArrayList em ordem crescente:");
        System.out.println(ascendingNumbers);

        ArrayList<Integer> descendingNumbers = new ArrayList<>(ascendingNumbers);
        Collections.reverse(descendingNumbers);

        System.out.println();
        System.out.println("ArrayList em ordem decrescente:");
        System.out.println(descendingNumbers);

        scanner.close();
    }

    public static int readValidQuantity(Scanner scanner) {
        int quantity;

        while (true) {
            System.out.print("Informe a quantidade de números que deseja gerar entre 1 e 100: ");
            quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity >= 1 && quantity <= 100) {
                return quantity;
            }

            System.out.println("Quantidade inválida. Digite um número entre 1 e 100.");
            System.out.println("Como os números não podem se repetir, a quantidade máxima é 100.");
        }
    }
}