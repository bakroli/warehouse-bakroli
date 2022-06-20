package com.warehouse.controller.order;

import com.warehouse.dto.order.OrderDto;
import com.warehouse.entity.order.Order;
import com.warehouse.entity.order.OrderDetail;
import com.warehouse.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "List all orders, not included order details")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderNumber}")
    @Operation(summary = "List one order with order details")
    public List<OrderDetail> getOrderFullByNumber(@PathVariable("orderNumber") Long orderNumber) {
        return orderService.getOrderFullByNumber(orderNumber);
    }

    @PostMapping
    public ResponseEntity<String> saveOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid order");
        }
        try {
            Long orderNumber = orderService.saveOrder(orderDto);
            return ResponseEntity.ok().body("Order save, Order number: " + orderNumber);
        } catch (IllegalArgumentException ie) {
            return ResponseEntity.badRequest().body("Invalid Order");
        }
    }

}
