package com.warehouse;

import com.warehouse.dto.order.OrderDetailDto;
import com.warehouse.dto.order.OrderDto;
import com.warehouse.dto.product.ProductDto;
import com.warehouse.entity.order.OrderDetail;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {

    private final String URL = "/orders";

    @Autowired
    TestRestTemplate template;

    @Test
    @Order(1)
    void O1_initProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(1001L);
        productDto.setName("TESTAMENT-1001");
        productDto.setDescription("testdescription");
        productDto.setValid(true);
        productDto.setListPrice(1000D);
        productDto.setMinPrice(800D);

        String answer = template.postForObject("/products", productDto, String.class);
        assertEquals("NEW Product save OK, product article number: 1001", answer);
    }

    @Test
    @Order(2)
    void O2_testInvalidOrderNoDetails() {
        OrderDto orderDto = new OrderDto();
        orderDto.setDate(LocalDate.now());
        orderDto.setOrderType("IN");
        orderDto.setComment("test");

        String answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Invalid order", answer);
    }

    @Test
    @Order(3)
    void O3_testInvalidOrderWrongOrderType() {
        OrderDto orderDto = new OrderDto();
        orderDto.setDate(LocalDate.now());
        orderDto.setOrderType("XXX");
        orderDto.setComment("test");

        String answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Invalid order", answer);
    }

    @Test
    @Order(4)
    void O4_testPostAndGetOrderGoodData() {
        OrderDto orderDto = new OrderDto();
        orderDto.setDate(LocalDate.now());
        orderDto.setOrderType("IN");
        orderDto.setComment("test");
        List<OrderDetailDto> details = new ArrayList<>();
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setArticleNumber(1001L);
        orderDetailDto.setNumberOfItem(2L);
        orderDetailDto.setPricePerItem(1000D);
        details.add(orderDetailDto);
        orderDto.setOrderDetails(details);

        String answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Order save, Order number: 1", answer);

        OrderDetail[] orderDetail = template.getForObject(URL + "/1", OrderDetail[].class);
        assertEquals(1, orderDetail.length);
        assertEquals(1001L, orderDetail[0].getProduct().getArticleNumber());
    }

}
