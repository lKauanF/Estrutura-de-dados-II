package exercicio05;

public class Contato {
    private String name;
    private String email;
    private String phone;

    public Contato(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void showData() {
        System.out.println("Nome: " + name);
        System.out.println("E-mail: " + email);
        System.out.println("Telefone: " + phone);
    }
}