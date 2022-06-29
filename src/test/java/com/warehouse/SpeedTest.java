package com.warehouse;

import com.warehouse.dto.order.OrderDetailDto;
import com.warehouse.dto.order.OrderDto;
import com.warehouse.dto.product.ProductDto;
import com.warehouse.entity.product.Product;
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
import java.util.Random;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpeedTest {

    @Autowired
    TestRestTemplate template;

    @Test
    @Order(1)
    void fullSpeedTestInit() {

        int productsNumber = 3000;
        int maxOrdersNumber = 5000;

        createProducts(productsNumber);
        createStockInit(productsNumber);
        createRandomOrder(maxOrdersNumber, productsNumber);
    }

    @Test
    @Order(2)
    void testGetAllProducts() {
        String URL = "/products";
        List<Product> products =  template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();
    }

    @Test
    @Order(3)
    void testGetAllProductsDto() {
        String URL = "/products";
        List<Product> products =  template.exchange(URL + "/dto",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

    }




    private void createProducts(int max) {
        String URL = "/products";

        ProductDto productDto = new ProductDto();
        Long articleNumberGenerator = 1L;

        for (int i = 0; i < max; i++) {
            productDto.setArticleNumber(articleNumberGenerator);
            productDto.setName("TEST-NAME" + articleNumberGenerator);
            productDto.setDescription("description");
            productDto.setValid(true);
            productDto.setListPrice(1000D);
            productDto.setMinPrice(800D);
            template.postForObject(URL, productDto, String.class);
            articleNumberGenerator++;
        }
    }

    private void createStockInit(int max) {
        String URL = "/orders";
        Long articleNumber = 1L;

        OrderDto orderDto = new OrderDto();

        orderDto.setDate(LocalDate.now());
        orderDto.setComment("initial");
        orderDto.setOrderType("IN");

        List<OrderDetailDto> details = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setArticleNumber(articleNumber);
            orderDetailDto.setNumberOfItem(1000L);
            orderDetailDto.setPricePerItem(800D);
            details.add(orderDetailDto);
            articleNumber++;
        }
        orderDto.setOrderDetails(details);
        template.postForObject(URL, orderDto, String.class);
    }

    private void createRandomOrder(int maxOrdersNumber, int maxProductsNumber) {
        Random random = new Random();
        String URL = "/orders";
        for (int i = 0; i < maxOrdersNumber; i++) {
            OrderDto orderDto = new OrderDto();
            orderDto.setComment("ordering");
            orderDto.setDate(LocalDate.now());
            orderDto.setOrderType(randomOrderType(random));
            List<OrderDetailDto> details = new ArrayList<>();
            int cMax = random.nextInt(6) + 1;
            for (int c = 0; c < cMax; c++) {
                OrderDetailDto orderDetailDto = new OrderDetailDto();
                orderDetailDto.setArticleNumber(random.nextLong(maxProductsNumber + 1) + 1);
                orderDetailDto.setNumberOfItem(random.nextLong(10)+1);
                orderDetailDto.setPricePerItem(900D);
                details.add(orderDetailDto);
            }
            orderDto.setOrderDetails(details);
            template.postForObject(URL, orderDto, String.class);
        }
    }

    private String randomOrderType(Random random) {
        if (random.nextInt(10) == 0) {
            return "IN";
        } else {
            return "OUT";
        }
    }


}
