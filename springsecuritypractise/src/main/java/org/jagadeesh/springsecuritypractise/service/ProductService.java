package org.jagadeesh.springsecuritypractise.service;
import org.jagadeesh.springsecuritypractise.entity.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductService {

    // fake database
    private final Map<Long, Product> db = new ConcurrentHashMap<>(Map.of(
            1L, new Product(1L, "Laptop", 55000),
            2L, new Product(2L, "Phone", 25000)
    ));

    @Cacheable(value = "products", key = "#id", unless = "#result == null")
    public Product getById(Long id) {
        System.out.println(">>> Hitting DB for id " + id);
        simulateSlow();
        return db.get(id);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product update(Product product) {
        System.out.println(">>> Updating DB for id " + product.getId());
        db.put(product.getId(), product);
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) {
        System.out.println(">>> Deleting from DB id " + id);
        db.remove(id);
    }

    private void simulateSlow() {
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}