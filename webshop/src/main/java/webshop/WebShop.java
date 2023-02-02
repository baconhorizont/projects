package webshop;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WebShop {

    private Store store;
    private CustomerService customerService;
    private Set<Cart> carts = new HashSet<>();
    private List<Order> orders = new ArrayList<>();

    public WebShop(Store store, CustomerService customerService) {
        validate(store, customerService);
        this.store = store;
        this.customerService = customerService;
    }

    public Store getStore() {
        return store;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public Set<Cart> getCarts() {
        return new HashSet<>(carts);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void addProduct(Product product) {
        store.addProduct(product);
    }

    public void addCustomer(Customer customer) {
        customerService.addCustomer(customer);
    }

    public void beginShopping(String email) {
        Customer newCustomer = customerService.getCustomerByEmail(email);
        if(carts.contains(new Cart(newCustomer))){
            throw new IllegalArgumentException("Customer with e-mail address: " + email + " has already began shopping!");
        }
        carts.add(new Cart(newCustomer));
    }

    public void addCartItem(String email, String barCode, int quantity) {
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity cannot be " + quantity + " or a negative number");
        }
        Cart actualCart = getActualCart(email);
        Product actualProduct = getActualProduct(barCode);

        actualCart.addCartItem(actualProduct,quantity);
    }

    public void rejectCart(String email) {
        Cart actualCart = getActualCart(email);
        carts.remove(actualCart);
    }

    public long order(String email, LocalDateTime timeOfOrder) {
        Customer customer = customerService.getCustomerByEmail(email);
        long id = generateId();
        Cart actualCart = getActualCart(email);
        orders.add(new Order(id,timeOfOrder,customer,actualCart.getProducts()));
        rejectCart(email);
        setCustomerToVIP(customer);
        return id;
    }

    private void setCustomerToVIP(Customer customer) {
        int numberOfOrders =  orders.stream()
                                  .map(Order::getCustomer)
                                  .filter(c -> c.equals(customer))
                                  .toList()
                                  .size();

        if(numberOfOrders >= 5){
            customer.setCustomerToVip();
        }
    }

    public Set<Customer> getCustomersByProduct(String barcode) {
        Product actualProduct = getActualProduct(barcode);
        return orders.stream()
                .filter(order -> order.hasCustomerBoughtProduct(actualProduct))
                .map(Order::getCustomer)
                .collect(Collectors.toSet());
    }

    public Map<Long, Integer> getTotalAmounts() {
        return orders.stream()
                .collect(Collectors.toMap(Order::getId,Order::getTotalAmount));
    }

    public Customer getCustomerWithMaxTotalAmount() {
        if (orders.isEmpty()){
            throw new IllegalArgumentException("No such customer.");
        }
         Customer customer = orders.get(0).getCustomer();
         int currentMax = 0;
        for (Order order : orders) {
            if (order.getTotalAmount() > currentMax){
                customer = order.getCustomer();
                currentMax = order.getTotalAmount();
            }
        }
        return customer;
    }

    public List<Order> listOrdersSortedByTotalAmounts() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getTotalAmount).reversed())
                .toList();
    }

    public List<Order> listOrdersSortedByDate() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getTimeOfOrder).reversed())
                .toList();
    }

    public boolean hasCustomerBoughtProduct(String email, String barcode) {
        Customer customer = customerService.getCustomerByEmail(email);
        Product product = store.getProductByBarcode(barcode);
        for (Order order : orders) {
            if (order.getCustomer().equals(customer) && order.getCart().containsKey(product)){
                return true;
            }
        }
        return false;
    }
    private long generateId() {
        return orders.size() + 1L;
    }
    private Cart getActualCart(String email) {
        return carts.stream().
                filter(cart -> cart.getCustomer().getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Customer with e-mail address " + email + " does not have an actual cart yet."));
    }
    private Product getActualProduct(String barCode) {
        return store.getProducts().stream()
                .filter(product -> product.getBarcode().equals(barCode)).
               findFirst()
               .orElseThrow(() -> new IllegalArgumentException("Not existing product"));
    }
    private void validate(Store store, CustomerService customerService) {
        if (store == null) {
            throw new IllegalArgumentException("Store cannot be empty!");
        }
        if (customerService == null) {
            throw new IllegalArgumentException("Customer service cannot be empty!");
        }
    }
}


