package com.warehouse.controller.html;

import com.warehouse.model.order.OrderDetailModel;
import com.warehouse.service.order.OrderHtmlService;
import com.warehouse.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderHtmlController {

    private final OrderHtmlService orderHtmlService;
    private final OrderService orderService;

    @Autowired
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
        List<OrderDetailModel> orderDetailModelList = orderHtmlService.getOrderDetailsByOrderNumber(orderNumber);
        model.addAttribute("details", orderDetailModelList);
        model.addAttribute("order", orderHtmlService.getOrderByNumber(orderNumber));
        model.addAttribute("sum", orderHtmlService.getOrderTotalSum(orderDetailModelList));
        return "orderdetails";
    }

}
