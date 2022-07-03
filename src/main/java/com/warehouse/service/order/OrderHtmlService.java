package com.warehouse.service.order;

import com.warehouse.entity.order.Order;
import com.warehouse.entity.order.OrderDetail;
import com.warehouse.model.order.OrderDetailModel;
import com.warehouse.model.order.OrderModel;
import com.warehouse.repository.order.OrderDetailRepository;
import com.warehouse.repository.order.OrderRepository;
import com.warehouse.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderHtmlService {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderHtmlService(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    // GET ALL ORDERS-MODEL

    public List<OrderModel> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderModel> orderModels = new ArrayList<>();
        for (Order order : orders) {
            orderModels.add(createOrderEntityToModel(order));
        }
        return orderModels;
    }

    private OrderModel createOrderEntityToModel(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderNumber(order.getOrderNumber());
        orderModel.setDate(order.getDate());
        orderModel.setOrderType(order.getOrderType().toString());
        orderModel.setComment(order.getComment());
        return orderModel;
    }

    // GET ORDER-DETAILS-MODEL BY ORDER NUMBER

    public List<OrderDetailModel> getOrderDetailsByOrderNumber(Long number) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderNumber(number);
        List<OrderDetailModel> orderDetailModels = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailModels.add(createOrderDetailEntityToModel(orderDetail));
        }
        return orderDetailModels;
    }

    private OrderDetailModel createOrderDetailEntityToModel(OrderDetail orderDetail) {
        OrderDetailModel orderDetailModel = new OrderDetailModel();
        orderDetailModel.setArticleNumber(orderDetail.getProduct().getArticleNumber());
        orderDetailModel.setName(orderDetail.getProduct().getName());
        orderDetailModel.setDescription(orderDetail.getProduct().getDescription());
        orderDetailModel.setCategory(orderDetail.getProduct().getProductCategory().getPrefix());
        orderDetailModel.setNumberOfItem(orderDetail.getNumberOfItem());
        orderDetailModel.setPricePerItem(orderDetail.getPricePerItem());
        return orderDetailModel;
    }

    public OrderModel getOrderByNumber(Long orderNumber) {
        Order order = orderRepository.findById(orderNumber).orElseThrow();
        return createOrderEntityToModel(order);
    }

    public Double getOrderTotalSum(List<OrderDetailModel> orderDetailModelList) {
        return orderDetailModelList.stream()
                .mapToDouble(p -> p.getPricePerItem() * p.getNumberOfItem())
                .sum();
    }

}
