package com.warehouse;

import com.warehouse.dto.order.OrderDetailDto;
import com.warehouse.dto.order.OrderDto;
import com.warehouse.dto.product.ProductDto;
import com.warehouse.entity.product.Product;
import com.warehouse.entity.product.ProductCategory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockCountTest {

    @Autowired
    TestRestTemplate template;

    @Test
    @Order(1)
    void C1_oneProductInit() {

        String URL = "/products";

        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(1L);
        productDto.setName("TESTAMENT");
        productDto.setDescription("testdescription");
        productDto.setValid(true);
        productDto.setListPrice(1000D);
        productDto.setMinPrice(800D);

        String answer = template.postForObject(URL, productDto, String.class);
        assertEquals("NEW Product save OK, product article number: 1", answer);

    }

    @Test
    @Order(2)
    void C2_orderInit() {
        String URL = "/orders";

        OrderDto orderDto = new OrderDto();

        orderDto.setDate(LocalDate.now());
        orderDto.setComment("initial");
        orderDto.setOrderType("IN");
        List<OrderDetailDto> details = new ArrayList<>();
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setArticleNumber(1L);
        orderDetailDto.setNumberOfItem(10L);
        orderDetailDto.setPricePerItem(800D);
        details.add(orderDetailDto);
        orderDto.setOrderDetails(details);
        String answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Order save, Order number: 1", answer);

        orderDto.setOrderType("OUT");
        details = new ArrayList<>();
        orderDetailDto = new OrderDetailDto();
        orderDetailDto.setArticleNumber(1L);
        orderDetailDto.setNumberOfItem(5L);
        orderDetailDto.setPricePerItem(800D);
        details.add(orderDetailDto);
        orderDto.setOrderDetails(details);
        answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Order save, Order number: 2", answer);

        orderDto.setOrderType("OUT");
        details = new ArrayList<>();
        orderDetailDto = new OrderDetailDto();
        orderDetailDto.setArticleNumber(1L);
        orderDetailDto.setNumberOfItem(3L);
        orderDetailDto.setPricePerItem(800D);
        details.add(orderDetailDto);
        orderDto.setOrderDetails(details);
        answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Order save, Order number: 3", answer);

        orderDto.setOrderType("IN");
        details = new ArrayList<>();
        orderDetailDto = new OrderDetailDto();
        orderDetailDto.setArticleNumber(1L);
        orderDetailDto.setNumberOfItem(12L);
        orderDetailDto.setPricePerItem(800D);
        details.add(orderDetailDto);
        orderDto.setOrderDetails(details);
        answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Order save, Order number: 4", answer);

        orderDto.setOrderType("OUT");
        details = new ArrayList<>();
        orderDetailDto = new OrderDetailDto();
        orderDetailDto.setArticleNumber(1L);
        orderDetailDto.setNumberOfItem(6L);
        orderDetailDto.setPricePerItem(800D);
        details.add(orderDetailDto);
        orderDto.setOrderDetails(details);
        answer = template.postForObject(URL, orderDto, String.class);
        assertEquals("Order save, Order number: 5", answer);
    }

    @Test
    @Order(3)
    void C3_testOneProductCount() {
        String URL = "/products/1";
        Product product = template.getForObject(URL, Product.class);
        assertEquals(8, product.getActualStock());
    }

    @Test
    @Order(4)
    void C4_testListProductCount() {
        String URL = "/products";
        List<Product> products =  template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        assert products != null;
        assertEquals(8, products.get(0).getActualStock());
    }

}
