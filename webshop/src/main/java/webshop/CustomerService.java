package webshop;

import java.text.Collator;
import java.util.*;

public class CustomerService {

    private Set<Customer> customers = new HashSet<>();

    public void addCustomer(Customer customer){
        validate(customer);
        customers.add(customer);
    }


    public Set<Customer> getCustomers() {
        return new HashSet<>(customers);
    }


    public List<Customer> listCustomersByCategoryGiven(CustomerCategory category) {
        return customers.stream()
                .filter(customer -> customer.getCategory().equals(category))
                .toList();
    }

    public Customer getCustomerByEmail(String email) {
        return customers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No customer with e-mail address: " + email));
    }

    public List<Customer> listCustomersSortedByEmail() {
        return customers.stream()
                .sorted()
                .toList();
    }

    public List<String> listCustomerNamesSorted() {
        return customers.stream()
                .map(Customer::getName)
                .sorted(Collator.getInstance(new Locale("hu","HU")))
                .toList();
    }
    private void validate(Customer newCustomer){
        if(newCustomer == null){
            throw new IllegalArgumentException("Customer cannot be empty.");
        }
        if(customers.contains(newCustomer)){
            throw new IllegalArgumentException("Customer with e-mail address: " + newCustomer.getEmail() + " is already registered.");
        }
    }
}
