package com.warehouse.service.order;

import com.warehouse.dto.order.OrderDetailDto;
import com.warehouse.dto.order.OrderDto;
import com.warehouse.entity.order.Order;
import com.warehouse.entity.order.OrderDetail;
import com.warehouse.entity.order.OrderType;
import com.warehouse.entity.product.Product;
import com.warehouse.repository.order.OrderDetailRepository;
import com.warehouse.repository.order.OrderRepository;
import com.warehouse.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    // GET ALL ORDERS

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // GET ONE ORDER WITH DETAILS

    public List<OrderDetail> getOrderFullByNumber(Long orderNumber) {
        return orderDetailRepository.findByOrderNumber(orderNumber);
    }

    // SAVE NEW ORDER

    public Long saveOrder(OrderDto orderDto) {
        if (!orderDtoValid(orderDto)) {
            throw new IllegalArgumentException();
        }
        Order order = convertOrderDtoToEntity(orderDto);
        if (!orderProductTrueValid(order)) {
            throw new IllegalArgumentException();
        }
        if (order.getOrderType() == OrderType.OUT && !orderPriceMinPriceValid(order)) {
            throw new IllegalArgumentException();
        }
        orderRepository.save(order);
        return order.getOrderNumber();
    }


    private boolean orderDtoValid(OrderDto orderDto) {
        List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetails();
        if (orderDetailDtos == null || orderDetailDtos.isEmpty()) {
            return false;
        }
        for (OrderDetailDto orderDetailDto : orderDetailDtos) {
            if (orderDetailDto.getNumberOfItem() == null || orderDetailDto.getPricePerItem() == null) {
                return false;
            }
            if (orderDetailDto.getNumberOfItem() <= 0 || orderDetailDto.getPricePerItem() < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean orderPriceMinPriceValid(Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Double orderPrice = orderDetail.getPricePerItem();
            Double productMinPrice = orderDetail.getProduct().getProductPrice().getMinPrice();
            if (orderPrice < productMinPrice) {
                return false;
            }
        }
        return true;
    }

    private boolean orderProductTrueValid(Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProduct().getValid() == Boolean.FALSE) {
                return false;
            }
        }
        return true;
    }


    private Order convertOrderDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setDate(orderDto.getDate());
        order.setComment(orderDto.getComment());
        if (orderDto.getOrderType().equals(OrderType.IN.toString())) {
            order.setOrderType(OrderType.IN);
        } else if (orderDto.getOrderType().equals(OrderType.OUT.toString())) {
            order.setOrderType(OrderType.OUT);
        } else {
            throw new IllegalArgumentException();
        }
        List<OrderDetail> orderDetails = new ArrayList<>();
        Map<Long, Long> stocks = new HashMap<>();
        for (OrderDetailDto orderDetailDto : orderDto.getOrderDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            Product product = productRepository.findByArticleNumber(orderDetailDto.getArticleNumber());
            if (product == null) {
                throw new IllegalArgumentException();
            }
            if (order.getOrderType() == OrderType.OUT) {
                Long productStock = orderDetailRepository.getProductStock(product.getId());
                if (productStock == null) {
                    productStock = 0L;
                }
                if (stocks.containsKey(product.getId())) {
                    stocks.put(product.getId(), stocks.get(product.getId()) - orderDetailDto.getNumberOfItem());
                } else {
                    stocks.put(product.getId(), productStock - orderDetailDto.getNumberOfItem());
                }
            }
            orderDetail.setProduct(product);
            orderDetail.setNumberOfItem(orderDetailDto.getNumberOfItem());
            orderDetail.setPricePerItem(orderDetailDto.getPricePerItem());
            orderDetails.add(orderDetail);
        }
        for (Map.Entry<Long, Long> e : stocks.entrySet()) {
            if (e.getValue() < 0) {
                throw new NumberFormatException();
            }
        }
        order.setOrderDetails(orderDetails);
        return order;
    }

}
