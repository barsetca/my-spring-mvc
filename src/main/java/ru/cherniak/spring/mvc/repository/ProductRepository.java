package ru.cherniak.spring.mvc.repository;

import org.springframework.stereotype.Repository;
import ru.cherniak.spring.mvc.exception.ResourceNotFoundException;
import ru.cherniak.spring.mvc.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private List<Product> products;
    private static AtomicLong newId;

    public ProductRepository() {
    }

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
        products.add(new Product(1L, "nail", 1));
        products.add(new Product(2L, "bolt", 10));
        products.add(new Product(3L, "nut", 5));
        products.add(new Product(4L, "staple", 15));
        products.add(new Product(5L, "screw", 2));
        newId = new AtomicLong(5);
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    public Product getProduct(long id) {
        Product product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product id =" + id + "is not exists");
        }
        return product;
    }

    public void save(String title, int cost) {
        long id = newId.incrementAndGet();
        Product product = new Product(id, title, cost);
        products.add(product);
    }

    public void deleteById(long id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (!removed) {
            throw new ResourceNotFoundException("Product id =" + id + "is not exists");
        }
    }
}
