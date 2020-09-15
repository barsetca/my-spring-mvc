package ru.cherniak.spring.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cherniak.spring.mvc.model.Product;
import ru.cherniak.spring.mvc.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(long id) {
        return productRepository.getProduct(id);
    }

    public void save(String title, int cost) {
        productRepository.save(title, cost);
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
