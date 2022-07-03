package com.warehouse;

import com.warehouse.dto.product.ProductCategoryDto;
import com.warehouse.entity.product.ProductCategory;
import org.junit.jupiter.api.*;
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
public class ProductCategoryTest {

    private final String URL = "/categories";

    @Autowired
    TestRestTemplate template;

    @Test
    @Order(1)
    void TPC00_testProductCategoryDatabaseIsEmpty() {
        List<ProductCategory> productCategories = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductCategory>>() {
                }).getBody();
        assertEquals(0, productCategories.size());
    }

    @Test
    @Order(2)
    void TPC201_testPostProductCategoryGoodData() {
        String answer = template.postForObject(URL, new ProductCategoryDto("XT", "XT-Telescope"), String.class);
        assertEquals("Save new category: XT", answer);
        answer = template.postForObject(URL, new ProductCategoryDto("X-T", "X-Telescope"), String.class);
        assertEquals("Save new category: X-T", answer);
    }

    @Test
    @Order(3)
    void TPC202_testGetAll() {
        List<ProductCategory> productCategories = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductCategory>>() {
                }).getBody();

        assertEquals(2, productCategories.size());
    }


    @Test
    @Order(4)
    void TPC203_testPostAndGetOneProductTypeGoodData() {
        ProductCategory productCategory = template.getForObject("/categories/XT", ProductCategory.class);
        assertEquals("XT", productCategory.getPrefix());
        assertEquals("XT-Telescope", productCategory.getName());
        productCategory = template.getForObject("/categories/X-T", ProductCategory.class);
        assertEquals("X-T", productCategory.getPrefix());
        assertEquals("X-Telescope", productCategory.getName());
    }

    @Test
    @Order(5)
    void TPC204_testDeleteProduct() {
        template.delete(URL + "/XT");
        ProductCategory productCategory = template.getForObject("/categories/XT", ProductCategory.class);
        assertNull(productCategory);
        productCategory = template.getForObject("/categories/X-T", ProductCategory.class);
        assertEquals("X-T", productCategory.getPrefix());
    }

    @Test
    void TPC401_testPostProductCategoryBadData() {
        String answer = template.postForObject(URL, new ProductCategoryDto("-XT", "X-Telescope"), String.class);
        assertEquals("INVALID DATA", answer);
        answer = template.postForObject(URL, new ProductCategoryDto("ABCDEFGHIJXXX", "X-Telescope"), String.class);
        assertEquals("INVALID DATA", answer);
        answer = template.postForObject(URL, new ProductCategoryDto("ABCDEFGHIJ", ""), String.class);
        assertEquals("INVALID DATA", answer);
        answer = template.postForObject(URL, new ProductCategoryDto("PS", null), String.class);
        assertEquals("INVALID DATA", answer);
    }

    @Test
    void TPC402_testGetOneProductTypeBadData() {
        ProductCategory productCategory = template.getForObject("/categories/XXX", ProductCategory.class);
        assertNull(productCategory);
    }

}
