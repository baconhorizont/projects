package webshop;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private long id;
    private LocalDateTime timeOfOrder;
    private Customer customer;
    private Map<Product,Integer> cart;

    public Order(long id, LocalDateTime timeOfOrder, Customer customer, Map<Product, Integer> cart) {
        validate(id,timeOfOrder,customer,cart);
        this.id = id;
        this.timeOfOrder = timeOfOrder;
        this.customer = customer;
        this.cart = cart;
    }
    public int getTotalAmount() {
        int result = cart.entrySet().stream()
                .mapToInt(p -> p.getKey().getPrice() * p.getValue())
                .sum();
        if (customer.getCategory().equals(CustomerCategory.SINGLE)){
            return result;
        } else {
            return (int) (result * (1 - customer.getCategory().getDiscount() / 100.0));
        }
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }


    public boolean hasCustomerBoughtProduct(Product product) {
        return cart.containsKey(product);
    }
    private void validate(long id,LocalDateTime timeOfOrder,Customer customer,Map<Product,Integer> cart){
        if(id <= 0){
            throw new IllegalArgumentException("Number " + id + " is not a valid id!");
        }
        if (timeOfOrder == null){
            throw new IllegalArgumentException("Time of order cannot be empty!");
        }
        if (customer == null){
            throw new IllegalArgumentException("Customer cannot be empty!");
        }
        if(cart == null || cart.isEmpty()){
            throw new IllegalArgumentException("Cart cannot be empty!");
        }
    }
}
