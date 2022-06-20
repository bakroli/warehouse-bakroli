package com.warehouse;

import com.warehouse.dto.product.ProductCategoryDto;
import com.warehouse.entity.product.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryITTest {

    @Autowired
    TestRestTemplate template;

    @Test
    void testPostProductCategoryGoodData() {
        String answer = template.postForObject("/categories", new ProductCategoryDto("XT", "X-Telescope"), String.class);
        assertEquals("Save new category: XT", answer);
        answer = template.postForObject("/categories", new ProductCategoryDto("X-T", "X-Telescope"), String.class);
        assertEquals("Save new category: X-T", answer);
    }

    @Test
    void testPostProductCategoryBadData() {
        String answer = template.postForObject("/categories", new ProductCategoryDto("-XT", "X-Telescope"), String.class);
        assertEquals("INVALID DATA", answer);
        answer = template.postForObject("/categories", new ProductCategoryDto("ABCDEFGHIJXXX", "X-Telescope"), String.class);
        assertEquals("INVALID DATA", answer);
        answer = template.postForObject("/categories", new ProductCategoryDto("ABCDEFGHIJ", ""), String.class);
        assertEquals("INVALID DATA", answer);
        answer = template.postForObject("/categories", new ProductCategoryDto("PS", null), String.class);
        assertEquals("INVALID DATA", answer);
    }

    @Test
    void testPostAndGetOneProductTypeGoodData() {
        template.postForObject("/categories", new ProductCategoryDto("XT", "XT-Telescope"), String.class);
        template.postForObject("/categories", new ProductCategoryDto("X-T", "X-Telescope"), String.class);
        ProductCategoryDto productCategoryDto = template.getForObject("/categories/XT", ProductCategoryDto.class);
        assertEquals("XT", productCategoryDto.getPrefix());
        assertEquals("XT-Telescope", productCategoryDto.getName());
        productCategoryDto = template.getForObject("/categories/X-T", ProductCategoryDto.class);
        assertEquals("X-T", productCategoryDto.getPrefix());
        assertEquals("X-Telescope", productCategoryDto.getName());
    }

    @Test
    void testPostAndGetOneProductTypeBadData() {
        template.postForObject("/categories", new ProductCategoryDto("XT", "X-Telescope"), String.class);
        ProductCategoryDto productCategoryDto = template.getForObject("/categories/X-T", ProductCategoryDto.class);
        assertNull(productCategoryDto);
    }

    @Test
    void testPostAndGetAll() {
        template.postForObject("/categories", new ProductCategoryDto("XT", "X-Telescope"), String.class);
        template.postForObject("/categories", new ProductCategoryDto("PS", "PopeScope"), String.class);

        List<ProductCategoryDto> productCategoriesDto =  template.exchange("/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductCategoryDto>>() {
                }).getBody();




    }





}
