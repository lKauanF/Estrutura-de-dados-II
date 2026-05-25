package exercicio05;

import java.util.ArrayList;

public class Agenda {
    private ArrayList<Contato> contacts;

    public Agenda() {
        this.contacts = new ArrayList<>();
    }

    public void include(Contato contact) {
        contacts.add(contact);
    }

    public boolean deleteByName(String name) {
        Contato contact = searchExactByName(name);

        if (contact != null) {
            contacts.remove(contact);
            return true;
        }

        return false;
    }

    public Contato searchExactByName(String name) {
        for (Contato contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }

        return null;
    }

    public ArrayList<Contato> searchByNamePart(String namePart) {
        ArrayList<Contato> foundContacts = new ArrayList<>();

        for (Contato contact : contacts) {
            if (contact.getName().toLowerCase().contains(namePart.toLowerCase())) {
                foundContacts.add(contact);
            }
        }

        return foundContacts;
    }

    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        System.out.println("Listando os itens da agenda:");

        for (int i = 0; i < contacts.size(); i++) {
            Contato contact = contacts.get(i);

            System.out.println("--");
            System.out.println("Posição " + i);
            contact.showData();
        }

        System.out.println("--");
    }

    public boolean isEmpty() {
        return contacts.isEmpty();
    }
}