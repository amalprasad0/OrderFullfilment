package com.product_service.product_service.search;

import org.springframework.stereotype.Service;

import com.product_service.product_service.entity.Product;

@Service
public class ProductSearchService {

    private final ProductSearchRepository searchRepository;

    public ProductSearchService(ProductSearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void indexProduct(Product product) {
        ProductDocument doc = ProductDocument.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .brand(product.getBrand())
                .categoryId(product.getProductCategory().getId())
                .productImageUrl(product.getProductImageUrl())
                .isActive(product.isActive())
                .isDeleted(product.isDeleted())
                .build();
        searchRepository.save(doc);
    }

    public Iterable<ProductDocument> searchByName(String name) {
        return searchRepository.findByProductNameContainingIgnoreCase(name);
    }
}
