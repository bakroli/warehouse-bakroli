package com.warehouse.repository.order;

import com.warehouse.dto.product.IProductStory;
import com.warehouse.dto.product.IStock;
import com.warehouse.entity.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "SELECT * FROM order_details WHERE order_number = ?", nativeQuery = true)
    List<OrderDetail> findByOrderNumber(long orderNumber);

    @Query(value =
            "SELECT SUM (number_of_item * " +
            " CASE " +
            "  WHEN orders.order_type = 'IN' THEN 1 " +
            "  WHEN orders.order_type = 'OUT' THEN -1 " +
            " END) " +
            "FROM order_details " +
            "JOIN orders ON orders.order_number = order_details.order_number " +
            "WHERE product_id = ?"
            ,nativeQuery = true)
    Long getProductStock(Long id);

    @Query (value =
            "SELECT product_id AS pid, " +
            "SUM (number_of_item * " +
            " CASE " +
            "  WHEN orders.order_type = 'IN' THEN 1 " +
            "  WHEN orders.order_type = 'OUT' THEN -1 " +
            " END ) AS stock " +
            "FROM order_details " +
            "JOIN orders ON orders.order_number = order_details.order_number " +
            "GROUP BY product_id"
            ,nativeQuery = true)
    List<IStock> countProductStock();


    @Query (value =
            "SELECT " +
            " orders.order_number, " +
            " orders.order_type, " +
            " orders.date, " +
            " orders.comment, " +
            " order_details.number_of_item, " +
            " order_details.price_per_item " +
            "FROM order_details " +
            "JOIN orders ON order_details.order_number = orders.order_number " +
            "JOIN products ON order_details.product_id = products.id " +
            "WHERE products.article_number = ? " +
            "ORDER BY orders.order_number"
            , nativeQuery = true)
    List<IProductStory> getProductHistory(Long articleNumber);
}
