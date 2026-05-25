package exercicio03;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Aluno> students = new ArrayList<>();

        System.out.println("Cadastro de alunos de Sistemas de Informação");
        System.out.println("Disciplina: Estrutura de Dados");
        System.out.println();

        System.out.print("Informe a quantidade de alunos que deseja cadastrar, no mínimo 3: ");
        int totalStudentsToRegister = scanner.nextInt();
        scanner.nextLine();

        while (totalStudentsToRegister < 3) {
            System.out.print("A quantidade mínima é 3. Informe novamente: ");
            totalStudentsToRegister = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 0; i < totalStudentsToRegister; i++) {
            System.out.println();
            System.out.println("Cadastro do aluno " + (i + 1));

            System.out.print("Número de matrícula: ");
            int registrationNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nome: ");
            String name = scanner.nextLine();

            double firstGrade = readValidGrade(scanner, "Nota do 1º bimestre: ");
            double secondGrade = readValidGrade(scanner, "Nota do 2º bimestre: ");

            Aluno student = new Aluno(registrationNumber, name, firstGrade, secondGrade);
            students.add(student);

            System.out.println("Aluno cadastrado com sucesso!");
        }

        System.out.println();
        System.out.println("Resultado dos alunos:");
        System.out.println("--");

        int approvedCount = 0;
        int failedCount = 0;
        int examCount = 0;

        double classAverageSum = 0.0;

        Aluno highestAverageStudent = students.get(0);
        Aluno lowestAverageStudent = students.get(0);

        for (Aluno student : students) {
            double average = student.calculateAverage();
            String status = student.getStatus();

            System.out.printf("%s obteve média: %.2f%n", student.getName(), average);
            System.out.println("Situação: " + status);
            System.out.println("--");

            classAverageSum += average;

            if (status.equals("APROVADO")) {
                approvedCount++;
            } else if (status.equals("REPROVADO")) {
                failedCount++;
            } else {
                examCount++;
            }

            if (average > highestAverageStudent.calculateAverage()) {
                highestAverageStudent = student;
            }

            if (average < lowestAverageStudent.calculateAverage()) {
                lowestAverageStudent = student;
            }
        }

        double classAverage = classAverageSum / students.size();

        System.out.println();
        System.out.println("Resumo da turma:");
        System.out.println("--");
        System.out.println("Total de alunos: " + students.size());
        System.out.println("Quantidade de alunos aprovados: " + approvedCount);
        System.out.println("Quantidade de alunos reprovados: " + failedCount);
        System.out.println("Quantidade de alunos em exame: " + examCount);
        System.out.printf("Média da classe: %.2f%n", classAverage);
        System.out.printf(
                "Aluno com maior média: %s, média %.2f%n",
                highestAverageStudent.getName(),
                highestAverageStudent.calculateAverage()
        );
        System.out.printf(
                "Aluno com menor média: %s, média %.2f%n",
                lowestAverageStudent.getName(),
                lowestAverageStudent.calculateAverage()
        );

        scanner.close();
    }

    public static double readValidGrade(Scanner scanner, String message) {
        double grade;

        while (true) {
            System.out.print(message);
            grade = scanner.nextDouble();
            scanner.nextLine();

            if (grade >= 0.0 && grade <= 10.0) {
                return grade;
            }

            System.out.println("Nota inválida. Digite uma nota entre 0.0 e 10.0.");
        }
    }
}