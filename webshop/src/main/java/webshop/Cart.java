package webshop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart {

    private Customer customer;
    private Map<Product, Integer> products = new HashMap<>();

    public Cart(Customer customer) {
        if(customer == null){
            throw  new IllegalArgumentException("Customer cannot be empty!");
        }
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, Integer> getProducts() {
        return new HashMap<>(products);
    }

    public void addCartItem(Product product, int amount) {
        validate(product,amount);
        if(products.containsKey(product)){
            products.put(product,products.get(product) + amount);
        } else{
            products.put(product,amount);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(customer, cart.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer);
    }

    private void validate(Product product,int amount){
        if(product == null){
            throw new IllegalArgumentException("Product cannot be empty.");
        }
        if(amount <= 0){
            throw  new IllegalArgumentException("Quantity cannot be 0 or a negative number");
        }
    }
}
