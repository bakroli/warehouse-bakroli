package com.warehouse.repository.order;

import com.warehouse.entity.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value = "SELECT * FROM order_details WHERE order_number = ?", nativeQuery = true)
    List<OrderDetail> findByOrderNumber(long orderNumber);

    @Query(value = "SELECT SUM (number_of_item * " +
            "CASE " +
            "WHEN orders.order_type = 'IN' THEN 1 " +
            "WHEN orders.order_type = 'OUT' THEN -1 " +
            "END) " +
            "FROM order_details " +
            "JOIN orders ON orders.order_number = order_details.order_number " +
            "WHERE product_id = ?;", nativeQuery = true)
    Long getProductStock(Long id);
}
