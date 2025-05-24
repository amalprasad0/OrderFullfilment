package com.product_service.product_service.search;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {

    @Id
    private Long id;
    private String productName;
    private String productDescription;
    private String brand;
    private Long categoryId;
    private String productImageUrl;
    private boolean isActive;
    private boolean isDeleted;
}