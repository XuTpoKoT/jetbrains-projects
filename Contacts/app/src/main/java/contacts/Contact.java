package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Contact implements Serializable {
    protected String name;
    protected String number;
    protected LocalDateTime created;
    protected LocalDateTime lastEdit;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final Pattern numberPattern = Pattern.compile("(\\+?\\w[ -])?(\\w{2,}[ -])?" +
            "((\\(\\w{2,}\\))|(\\(\\w{2,}\\)[ -])?(\\w{2,}[ -]?)*)|" +
            "\\+?\\(\\w+\\)([ -]\\w{2,})*|" +
            "\\+?\\w");

    protected Contact(String name, String number) {
        this.name = name;
        this.number = number;
        this.created = LocalDateTime.now();
        this.lastEdit = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (numberPattern.matcher(number).matches())
            this.number = number;
        else {
            this.number = null;
            System.out.println("Wrong number format!");
        }
    }

    public void setLastEdit(LocalDateTime lastEdit) {
        this.lastEdit = lastEdit;
    }

    public ArrayList<String> getFields() {
        return new ArrayList<String>(Arrays.asList("name", "number"));
    }

    public void setField(String field, String value) {
        if (field.equals("name"))
            this.setName(value);
        else
            this.setNumber(value);
    }

    public static class Builder {
        protected String name;
        protected String number;

        Builder () {}

        public Contact.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Contact.Builder setNumber(String number) {
            this.number = number;
            return this;
        }
    }

    public void printInfo() { }

    public void checkNumberPattern() {
        System.out.println(numberPattern.matcher("+0 (123) 456-789-ABcd").matches());
        System.out.println(numberPattern.matcher("(123) 234 345-456").matches());
        System.out.println(numberPattern.matcher("(123)").matches());
        System.out.println(numberPattern.matcher("123-(456)").matches());
        System.out.println(numberPattern.matcher("+(phone)").matches());
        System.out.println(numberPattern.matcher("9").matches());
        System.out.println(numberPattern.matcher("+9").matches());
        System.out.println(numberPattern.matcher("988").matches());

        System.out.println(!numberPattern.matcher("(123)234 345-456").matches());
    }
}
