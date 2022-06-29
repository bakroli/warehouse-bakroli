package com.warehouse.controller.productHTML;

import com.warehouse.service.product.ProductService;
import org.springframework.stereotype.Controller;

@Controller("/products/html")
public class productHTMLController {

    private ProductService productService;

    public productHTMLController(ProductService productService) {
        this.productService = productService;
    }


}
