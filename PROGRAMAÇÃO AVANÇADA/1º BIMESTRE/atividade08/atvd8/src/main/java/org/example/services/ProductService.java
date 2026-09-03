package org.example.services;

import org.example.entities.Product;
import org.example.exceptions.EntityNotFoundException;
import org.example.exceptions.ValidationException;
import org.example.repository.ProductRepository;
import org.example.utils.ValidationUtils;

import java.util.List;
import java.util.UUID;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product registerProduct(String description, double hourlyRate) {
        ValidationUtils.validateNotBlank(description, "descrição do serviço");
        ValidationUtils.validatePositive(hourlyRate, "valor por hora");

        Product product = new Product(description.trim(), hourlyRate);
        productRepository.save(product);
        return product;
    }

    public Product getProductById(UUID id) {
        ValidationUtils.validateNotNull(id, "ID do serviço");
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço com ID " + id + " não encontrado."));
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchByDescription(String keyword) {
        ValidationUtils.validateNotBlank(keyword, "palavra-chave de busca");
        return productRepository.findByDescriptionContaining(keyword.trim());
    }

    public Product updateProduct(UUID id, String newDescription, double newHourlyRate) {
        Product product = getProductById(id);

        if (newDescription != null && !newDescription.trim().isEmpty()) {
            product.setDescription(newDescription.trim());
        }

        if (newHourlyRate > 0) {
            product.setHourlyRate(newHourlyRate);
        } else if (newHourlyRate < 0) {
            throw new ValidationException("O valor por hora não pode ser negativo.");
        }

        productRepository.update(product);
        return product;
    }

    public void deleteProduct(UUID id) {
        getProductById(id); // Ensures product exists
        productRepository.deleteById(id);
    }

    public void clearAllProducts() {
        productRepository.deleteAll();
    }
}
