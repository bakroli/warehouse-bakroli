package com.warehouse;


import com.warehouse.dto.product.ProductCategoryDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductITTest {

    private final String URL = "/products";

    @Autowired
    TestRestTemplate template;

    @Test
    @Order(1)
    void P01_testProductDatabaseIsEmpty() {
        List<Product> products =  template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        assertEquals(0,products.size());
    }

    @Test
    void P02_testPostOneProductGoodDataNoProductCategory() {
        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(1L);
        productDto.setName("TEST-NAME");
        productDto.setDescription("testdescription");
        productDto.setValid(true);
        productDto.setListPrice(1000D);
        productDto.setMinPrice(800D);

        String answer = template.postForObject(URL, productDto, String.class);
        assertEquals("NEW Product save OK, product article number: 1", answer);
    }

    @Test
    void P03_testPostProductGoodDataByProductCategory() {
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setPrefix("PS");
        productCategoryDto.setName("PopeScope");
        template.postForObject("/categories", productCategoryDto, String.class);

        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(2L);
        productDto.setName("TESTAMENT");
        productDto.setDescription("testdescription");
        productDto.setProductCategory("PS");
        productDto.setValid(true);
        productDto.setListPrice(1000D);
        productDto.setMinPrice(800D);

        String answer = template.postForObject(URL, productDto, String.class);
        assertEquals("NEW Product save OK, product article number: 2", answer);
    }



    @Test
    void P04_testPostMinimumProductData() {
        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(4L);
        productDto.setName("MINIMUM-PRODUCT");
        productDto.setDescription("minumimi");

        String answer = template.postForObject(URL, productDto, String.class);
        assertEquals("NEW Product save OK, product article number: 4", answer);
    }

    @Test
    void P05_testAgainProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(5L);
        productDto.setName("AGAIN");
        productDto.setDescription("againdes");
        String answer = template.postForObject(URL, productDto, String.class);
        assertEquals("NEW Product save OK, product article number: 5", answer);
        answer = template.postForObject(URL, productDto, String.class);
        assertEquals("ERROR new product save", answer);
    }

    @Test
    void P06_testInvalidData() {
        ProductDto productDto = new ProductDto();
        productDto.setArticleNumber(6L);
        productDto.setName("INVALID");
        productDto.setDescription("");
        String answer = template.postForObject(URL, productDto, String.class);
        assertEquals("INVALID DATA", answer);
    }










}
