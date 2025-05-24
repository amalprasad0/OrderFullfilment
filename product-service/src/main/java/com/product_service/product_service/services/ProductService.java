package com.product_service.product_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product_service.product_service.entity.Product;
import com.product_service.product_service.entity.ProductRatings;
import com.product_service.product_service.interfaces.IProductCategory;
import com.product_service.product_service.interfaces.IProductService;
import com.product_service.product_service.models.AddProductRating;
import com.product_service.product_service.models.Response;
import com.product_service.product_service.repository.ProductRatingRepository;
import com.product_service.product_service.repository.ProductRepository;
import com.product_service.product_service.search.ProductSearchService;

import org.springframework.cache.annotation.Cacheable;

@Service
public class ProductService implements IProductService {
    @Autowired
    public IProductCategory productCategoryRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public ProductRatingRepository productRatingRepository;
    @Autowired
private ProductSearchService productSearchService;
    @Override
    public Response<Long> AddProduct(com.product_service.product_service.models.AddProduct entity) {
        try{
            var productCategoryOpt = productCategoryRepository.findById(entity.getProductCategoryId());
            if (productCategoryOpt.isEmpty()) {
                return Response.error("Failed to add product: Product Category ID does not exist");
            }
            Product product = new Product();
            product.setProductName(entity.getProductName());
            product.setProductDescription(entity.getProductDescription());
            product.setBrand(entity.getBrand());
            product.setProductCategory(productCategoryOpt.get());
            product.setCreatedBy(entity.getCreatedBy());
            product.setUpdatedBy(entity.getUpdatedBy());
            product.setDeleted(entity.isDeleted());
            product.setProductImageUrl(entity.getProductImageUrl());
            product.setActive(entity.isActive());

            var savedProduct=productRepository.save(product);
            var id = savedProduct.getId(); 
            if (id == null) {
                return Response.error("Failed to add product: ID is null");
            }
            productSearchService.indexProduct(savedProduct);
            return Response.success(id, "Product added successfully");
        }catch(Exception e){
            return Response.error("Failed to add product: " + e.getMessage());
        }
    }
    @Override
    public Response<List<Product>> GetAllProduct(){
        try{
            var products = productRepository.findAll();
            if (products.isEmpty()) {
                return Response.error("Failed to get all products: No products found");
            }
            return Response.success(products, "Products retrieved successfully");
        }catch(Exception e){
            return Response.error("Failed to get all products: " + e.getMessage());
        }
    }
    @Override
    @Cacheable(value = "product", key = "#id")
    public Response<Product> GetProductById(Long id) {
        try{
            var productOpt = productRepository.findById(id);
            if (productOpt.isEmpty()) {
                return Response.error("Failed to get product: Product ID does not exist");
            }
            return Response.success(productOpt.get(), "Product retrieved successfully");
        }catch(Exception e){
            return Response.error("Failed to get product: " + e.getMessage());
        }
    }
    @Override
    public Response<Boolean> AddProductRating(AddProductRating productRating) {
        try {
            var productOpt = productRepository.findById(productRating.getProductId());
            if (productOpt.isEmpty()) {
                return Response.error("Failed to add rating: Product ID does not exist");
            }
            Product product = productOpt.get();
            if(productRating.getRating() < 1 || productRating.getRating() > 5) {
                return Response.error("Failed to add rating: Rating must be between 1 and 5");
            }
            if(productRating.getComment() == null || productRating.getComment().isEmpty()) {
                return Response.error("Failed to add rating: Comment cannot be empty");
            }
            if(productRating.getUserId() == null || productRating.getUserId().isEmpty()) {
                return Response.error("Failed to add rating: User ID cannot be empty");
            }
            // if(product.isDeleted() || !product.isActive()) {
            //     return Response.error("Failed to add rating: Product is not active or has been deleted");
            // }
            ProductRatings existingRating = productRatingRepository.findAll()
                .stream()
                .filter(r -> r.getProductId().equals(product) && r.getUserId().equals(Long.parseLong(productRating.getUserId())))
                .findFirst()
                .orElse(null);
            if (existingRating != null) {
                existingRating.setRating(productRating.getRating());
                existingRating.setReview(productRating.getComment());
                existingRating.setUpdatedBy(productRating.getUserId());
                ProductRatings updatedProductRating = productRatingRepository.save(existingRating);
                if (updatedProductRating == null) {
                    return Response.error("Failed to update rating: Rating could not be updated");
                }
                return Response.success(true, "Rating updated successfully");
                // return Response.error("Failed to add rating: User has already rated this product");
            }
            ProductRatings productRatingEntity = new ProductRatings();
            productRatingEntity.setProductId(product);
            productRatingEntity.setUserId(Long.parseLong(productRating.getUserId()));
            productRatingEntity.setRating(productRating.getRating());
            productRatingEntity.setReview(productRating.getComment());
            productRatingEntity.setCreatedBy(productRating.getUserId());
            productRatingEntity.setUpdatedBy(productRating.getUserId());
            productRatingEntity.setDeleted(false);
            ProductRatings savedProductRating = productRatingRepository.save(productRatingEntity);
            if (savedProductRating == null) {
                return Response.error("Failed to add rating: Rating could not be saved");
            }
            return Response.success(true, "Rating added successfully");
        } catch (Exception e) {
            return Response.error("Failed to add rating: " + e.getMessage());
        }
    }
}
