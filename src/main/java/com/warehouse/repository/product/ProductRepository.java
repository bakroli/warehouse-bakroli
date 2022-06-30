package com.warehouse.repository.product;

import com.warehouse.model.dao.ProductDao;
import com.warehouse.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE article_number = ?", nativeQuery = true)
    Product findByArticleNumber(Long articleNumber);


    @Query(value =
            "SELECT products.article_number, " +
                    "products.name, " +
                    "products.description, " +
                    "products.product_category_prefix, " +
                    "products.valid, " +
                    "product_price.list_price, " +
                    "product_price.min_price, " +
                    "SUM (number_of_item * " +
                    "CASE " +
                    "WHEN orders.order_type = 'IN' THEN 1 " +
                    "WHEN orders.order_type = 'OUT' THEN -1 " +
                    "END) AS stock " +
                    "FROM products " +
                    "LEFT JOIN order_details ON products.id = order_details.product_id " +
                    "LEFT JOIN orders ON orders.order_number = order_details.order_number " +
                    "LEFT JOIN product_price ON products.id = product_price.id " +
                    "GROUP BY products.article_number, " +
                    "products.name, " +
                    "products.description, " +
                    "products.product_category_prefix, " +
                    "products.valid, " +
                    "product_price.list_price, " +
                    "product_price.min_price " +
                    "ORDER BY article_number ASC"
            , nativeQuery = true)
    List<ProductDao> getAllProductDao();

    @Query(value =
            "SELECT products.article_number, " +
                    "products.name, " +
                    "products.description, " +
                    "products.product_category_prefix, " +
                    "products.valid, " +
                    "product_price.list_price, " +
                    "product_price.min_price, " +
                    "SUM (number_of_item * " +
                    "CASE " +
                    "WHEN orders.order_type = 'IN' THEN 1 " +
                    "WHEN orders.order_type = 'OUT' THEN -1 " +
                    "END) AS stock " +
                    "FROM products " +
                    "LEFT JOIN order_details ON products.id = order_details.product_id " +
                    "LEFT JOIN orders ON orders.order_number = order_details.order_number " +
                    "LEFT JOIN product_price ON products.id = product_price.id " +
                    "WHERE  products.article_number = ? " +
                    "GROUP BY products.article_number, " +
                    "products.name, " +
                    "products.description, " +
                    "products.product_category_prefix, " +
                    "products.valid, " +
                    "product_price.list_price, " +
                    "product_price.min_price " +
                    "ORDER BY article_number ASC"
            , nativeQuery = true)
    ProductDao getProductDaoByArticleNumber(Long articleNumber);


}
