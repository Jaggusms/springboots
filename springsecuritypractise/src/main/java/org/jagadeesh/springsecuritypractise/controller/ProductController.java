package org.jagadeesh.springsecuritypractise.controller;
import org.jagadeesh.springsecuritypractise.entity.Product;
import org.jagadeesh.springsecuritypractise.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        return service.update(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
