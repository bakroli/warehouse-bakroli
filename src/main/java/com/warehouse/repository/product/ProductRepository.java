package com.warehouse.repository.product;

import com.warehouse.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE article_number = ?", nativeQuery = true)
    Product findByArticleNumber(Long articleNumber);
}
