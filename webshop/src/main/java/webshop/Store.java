package webshop;

import java.text.Collator;
import java.util.*;

public class Store {

    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        validate(product);
        products.add(product);
    }


    public Set<Product> getProducts() {
        return new HashSet<>(products);
    }

    public Product getProductByBarcode(String barcode) {
        return products.stream()
                .filter(product -> product.getBarcode().equals(barcode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No product with barcode: " + barcode));
    }


//    public Map<ProductCategory, List<Product>> getProductsByCategory() {
//        Map<ProductCategory, List<Product>> result = new HashMap<>();
//        for (Product product: products) {
//            if(!result.containsKey(product.getCategory())){
//                result.put(product.getCategory(),new ArrayList<>());
//            }
//            result.get(product.getCategory()).add(product);
//        }
//
//         return result;
//    }

    public Map<ProductCategory, List<Product>> getProductsByCategory() {
        Map<ProductCategory, List<Product>> result = new HashMap<>();
        products.forEach(product -> {
            if(!result.containsKey(product.getCategory())){
            result.put(product.getCategory(),new ArrayList<>());
            }
            result.get(product.getCategory()).add(product);
        });
        return result;
    }

    public Product getCheapestProductByCategory(ProductCategory category) {
        return products.stream()
                .filter(product -> product.getCategory().equals(category))
                .min(Comparator.comparing(Product::getPrice))
                .orElseThrow(() -> new IllegalArgumentException("Empty category"));
    }

    public List<Product> listProductsSortedByPrice() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
    }

    public List<Product> listProductsSortedByName() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getName,Collator.getInstance(new Locale("hu","HU"))))
                .toList();
    }
    private void validate(Product product) {
        if(product == null){
            throw new IllegalArgumentException("Product cannot be empty!");
        }
        if(products.contains(product)){
            throw new IllegalArgumentException("Product with barcode: " + product.getBarcode() +" is already registered.");
        }
    }
}
