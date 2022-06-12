package contacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class OrganizationContact extends Contact{
    protected String address;

    protected OrganizationContact(String name, String number, String address) {
        super(name, number);
        this.address = address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    @Override
    public ArrayList<String> getFields() {
        return new ArrayList<String>(Arrays.asList("name", "address", "number"));
    }

    @Override
    public void setField(String field, String value) {
        if (field.equals("name"))
            this.setName(value);
        else if (field.equals("address"))
            this.setAddress(value);
        else
            this.setNumber(value);
    }

    public static class Builder extends Contact.Builder {
        protected String address;

        Builder () {}

        public OrganizationContact.Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public OrganizationContact build() {
            return new OrganizationContact(name, number, address);
        }
    }

    @Override
    public void printInfo() {
        System.out.printf("Organization name: %s\n", this.name);
        System.out.printf("Address: %s\n", this.address);
        if (this.number == null)
            System.out.println("[no number]");
        else
            System.out.printf("Number: %s\n", this.number);
        System.out.printf("Time created: %s\n", LocalDateTime.parse(this.created.format(formatter), formatter));
        System.out.printf("Time last edit: %s\n\n", LocalDateTime.parse(this.lastEdit.format(formatter), formatter));
    }
}
