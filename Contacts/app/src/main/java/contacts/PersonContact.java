package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class PersonContact extends Contact{
    protected String surname;
    protected LocalDate birthDate;
    protected Character gender;

    protected PersonContact(String name, String number, String surname, LocalDate birthDate, Character gender) {
        super(name, number);
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    protected void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    protected void setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
            this.birthDate = null;
        }
    }

    public char getGender() {
        return gender;
    }

    protected void setGender(String gender) {
        try {
            if (gender.charAt(0) == 'M' || gender.charAt(0) == 'F')
                this.gender = gender.charAt(0);
            else {
                System.out.println("Bad gender!");
                this.gender = null;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Bad gender!");
        }
    }

    @Override
    public ArrayList<String> getFields() {
        return new ArrayList<String>(Arrays.asList("name", "surname", "birth", "gender", "number"));
    }

    @Override
    public void setField(String field, String value) {
        if (field.equals("name"))
            this.setName(value);
        else if (field.equals("surname"))
            this.setSurname(value);
        else if (field.equals("birth"))
            this.setBirthDate(value);
        else if (field.equals("gender"))
            this.setGender(value);
        else
            this.setNumber(value);
    }

    public static class Builder extends Contact.Builder {
        protected String surname ;
        protected LocalDate birthDate;
        protected Character gender;

        Builder () {}

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setGender(Character gender) {
            this.gender = gender;
            return this;
        }

        public PersonContact build() {
            return new PersonContact(name, number, surname, birthDate, gender);
        }
    }

    @Override
    public void printInfo() {
        System.out.printf("Name: %s\n", this.name);
        System.out.printf("Surname: %s\n", this.surname);
        if (this.birthDate == null)
            System.out.println("Birth date: [no data]");
        else
            System.out.printf("Birth date: %s\n", this.birthDate);
        if (this.gender == null)
            System.out.println("Gender: [no data]");
        else
            System.out.printf("Gender: %s\n", this.gender);
        if (this.number == null)
            System.out.println("[no number]");
        else
            System.out.printf("Number: %s\n", this.number);
        System.out.printf("Time created: %s\n", LocalDateTime.parse(this.created.format(formatter), formatter)); // TODO: change
        System.out.printf("Time last edit: %s\n\n", LocalDateTime.parse(this.lastEdit.format(formatter), formatter));
    }
}
