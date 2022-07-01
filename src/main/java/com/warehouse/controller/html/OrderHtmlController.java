package com.warehouse.controller.html;

import com.warehouse.service.order.OrderHtmlService;
import com.warehouse.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderHtmlController {

    private OrderHtmlService orderHtmlService;
    private OrderService orderService;

    public OrderHtmlController(OrderHtmlService orderHtmlService, OrderService orderService) {
        this.orderHtmlService = orderHtmlService;
        this.orderService = orderService;
    }

    @GetMapping("/orders/html")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/orders/html/{orderNumber}")
    public String getOrderByNumber(@PathVariable("orderNumber") Long orderNumber, Model model) {
        model.addAttribute("details", orderHtmlService.getOrderDetailsByOrderNumber(orderNumber));
        model.addAttribute("order", orderHtmlService.getOrderByNumber(orderNumber));
        return "orderdetails";
    }



}
