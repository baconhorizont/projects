package webshop;

import java.util.Objects;

public class Customer implements Comparable<Customer> {

    private String name;
    private String email;
    private CustomerCategory category;

    public Customer(String name, String email) {
        validate(name,email);
        this.name = name;
        this.email = email;
        this.category = CustomerCategory.SINGLE;
    }
    public void setCustomerToVip() {
        this.category = CustomerCategory.VIP;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public CustomerCategory getCategory() {
        return category;
    }

    @Override
    public int compareTo(Customer o) {
        return this.email.compareTo(o.email);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    private void validate(String name,String email){
        if(name == null || email == null){
            throw new IllegalArgumentException("Name or e-mail address cannot be empty!");
        }
        if(name.isBlank() || email.isBlank()){
            throw new IllegalArgumentException("Name or e-mail address cannot be empty!");
        }
    }

}
