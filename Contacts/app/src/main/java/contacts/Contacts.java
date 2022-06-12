package contacts;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Contacts implements Serializable {

    private ArrayList<Contact> contacts;
    private static final Scanner scanner = new Scanner(System.in); // ?
    private final Pattern typePattern = Pattern.compile("(person|organization)");
    private final Pattern fieldPattern = Pattern.compile("(name|surname|number)");
    private static final long serialVersionUID = 1L;

    public Contacts () {
        this.contacts = new ArrayList<Contact>();
    }

    public void add() {
        System.out.println("Enter the type (person, organization):");
        String type = scanner.nextLine();
        if (!typePattern.matcher(type).matches()) {
            System.out.println("Wrong contact type!");
            return;
        }

        Contact contact;

        if (type.equals("person")) {
            PersonContact.Builder builder = new PersonContact.Builder();

            System.out.println("Enter the name:"); // code duplication
            String name = scanner.nextLine();
            builder.setName(name);

            System.out.println("Enter the surname:");
            String surname = scanner.nextLine();
            builder.setSurname(surname);

            System.out.println("Enter the birth date:");
            try {
                LocalDate birthData = LocalDate.parse(scanner.nextLine());
                builder.setBirthDate(birthData);
            } catch (DateTimeParseException e) {
                System.out.println("Bad birth date!");
            }

            System.out.println("Enter the gender (M, F):");
            try {
                Character gender = scanner.nextLine().charAt(0);
                if (gender == 'M' || gender == 'F')
                    builder.setGender(gender);
                else
                    System.out.println("Bad gender!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Bad gender!");
            }

            System.out.println("Enter the number:"); // code duplication
            String number = scanner.nextLine();
            if (Contact.numberPattern.matcher(number).matches())
                builder.setNumber(number);
            else
                System.out.println("Wrong number format!");

            contact = builder.build(); // code duplication
        } else {
            OrganizationContact.Builder builder = new OrganizationContact.Builder();

            System.out.println("Enter the name:");
            String name = scanner.nextLine();
            builder.setName(name);

            System.out.println("Enter the address:");
            String address = scanner.nextLine();
            builder.setAddress(address);

            System.out.println("Enter the number:");
            String number = scanner.nextLine();
            if (Contact.numberPattern.matcher(number).matches())
                builder.setNumber(number);
            else
                System.out.println("Wrong number format!");

            contact = builder.build();
        }
        this.contacts.add(contact);

        System.out.println("The record added.");
    }

    public void remove(int recordNumber) {
        this.contacts.remove(recordNumber - 1);
        System.out.println("The record removed!");
    }

    public void edit(int recordNumber) {
        Contact contact;
        contact = this.contacts.get(recordNumber - 1);

        if (contact instanceof  PersonContact)
            System.out.println("Select a field (name, surname, birth, gender, number):"); // TODO: getFields
        if (contact instanceof OrganizationContact)
            System.out.println("Select a field (name, address, number):");
        String field = scanner.nextLine();
        ArrayList<String> fields = contact.getFields();
        if (!fields.contains(field)) {
            System.out.println("Wrong field!");
            return;
        }
        System.out.printf("Enter %s:", field);
        String newValue = scanner.nextLine();

        contact.setField(field, newValue);
        contact.setLastEdit(LocalDateTime.now());
        System.out.println("The record updated!");
    }

    public int count() {
        int size = 0;
        if (this.contacts != null)
            size = contacts.size();
        return size;
    }

    public int list() {
        if (this.count() == 0) {
            System.out.println("No records");
            return 1;
        }

        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, contacts.get(i).getName());
        }
        return 0;
    }

    public void printInfo(int recordNumber) {
        this.contacts.get(recordNumber - 1).printInfo();
    }

    public ArrayList<Contact> search(String searchQuery) {
        searchQuery = searchQuery.toLowerCase();
        final Pattern searchingPattern = Pattern.compile(".*" + searchQuery + ".*"); // TODO: change
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Contact contact : this.contacts) {
            if (searchingPattern.matcher(contact.getName().toLowerCase()).matches() ||
                searchingPattern.matcher(contact.getNumber().toLowerCase()).matches() ||
                contact instanceof PersonContact && searchingPattern.matcher(((PersonContact) contact).getSurname().toLowerCase()).matches()) {
                contacts.add(contact);
            }
        }

        return contacts;
    }

    public void checkFieldPattern() {
        System.out.println(fieldPattern.matcher("number").matches());
        System.out.println(fieldPattern.matcher("name").matches());

        System.out.println(!fieldPattern.matcher("1name").matches());
        System.out.println(!fieldPattern.matcher("name1").matches());
        System.out.println(!fieldPattern.matcher("123").matches());
    }
}
