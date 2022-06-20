package com.warehouse;

import com.warehouse.dto.product.ProductCategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryTest {

    private final String PRODUCT_CATEGORY_URL = "/categories";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllCategoryTest() {

        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setPrefix("XL");
        productCategoryDto.setName("X-XL-Telescope");

        postProductCategory(PRODUCT_CATEGORY_URL, productCategoryDto);

        ResponseEntity<ProductCategoryDto[]> response = restTemplate.getForEntity(PRODUCT_CATEGORY_URL, ProductCategoryDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    private void postProductCategory(String product_category_url, ProductCategoryDto productCategoryDto) {
        final HttpEntity<ProductCategoryDto> httpEntity = createProductCategoryDtoHttpEntityWithMediaTypeJson(productCategoryDto);
        restTemplate.postForEntity(product_category_url, httpEntity, String.class);
    }

    private HttpEntity<ProductCategoryDto> createProductCategoryDtoHttpEntityWithMediaTypeJson(ProductCategoryDto productCategoryDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(productCategoryDto, headers);
    }

}
