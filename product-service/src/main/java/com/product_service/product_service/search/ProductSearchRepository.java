package com.product_service.product_service.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {
    Iterable<ProductDocument> findByProductNameContainingIgnoreCase(String name);
}