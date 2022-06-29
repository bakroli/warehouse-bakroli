package com.warehouse.controller.productHTML;

import com.warehouse.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class productHTMLController {

    private ProductService productService;

    public productHTMLController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/html")
    public String getAllProductsDto(Model model) {
        model.addAttribute("title", "Products");
        model.addAttribute("products", productService.getAllProductDto());
        return "products";
    }

    @GetMapping("/products/html/{articleNumber}")
    public String getProductHistory(@PathVariable("articleNumber") Long articleNumber, Model model) {
        //model.addAttribute("producthistory", productService.getProductHistory(articleNumber));
        return "producthistory";
    }


}
