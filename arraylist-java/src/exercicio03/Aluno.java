package exercicio03;

public class Aluno {
    private int registrationNumber;
    private String name;
    private double firstGrade;
    private double secondGrade;

    public Aluno(int registrationNumber, String name, double firstGrade, double secondGrade) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        setFirstGrade(firstGrade);
        setSecondGrade(secondGrade);
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFirstGrade() {
        return firstGrade;
    }

    public void setFirstGrade(double firstGrade) {
        if (firstGrade >= 0.0 && firstGrade <= 10.0) {
            this.firstGrade = firstGrade;
        } else {
            this.firstGrade = 0.0;
        }
    }

    public double getSecondGrade() {
        return secondGrade;
    }

    public void setSecondGrade(double secondGrade) {
        if (secondGrade >= 0.0 && secondGrade <= 10.0) {
            this.secondGrade = secondGrade;
        } else {
            this.secondGrade = 0.0;
        }
    }

    public double calculateAverage() {
        return (firstGrade + secondGrade) / 2.0;
    }

    public String getStatus() {
        double average = calculateAverage();

        if (average < 4.0) {
            return "REPROVADO";
        }

        if (average < 6.0) {
            return "EXAME";
        }

        return "APROVADO";
    }
}