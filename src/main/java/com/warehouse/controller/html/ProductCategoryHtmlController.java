package com.warehouse.controller.html;

import com.warehouse.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductCategoryHtmlController {

    private ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryHtmlController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/categories/html")
    public String getAllProductsDto(Model model) {
        model.addAttribute("categories", productCategoryService.getAllCategories());
        return "productcategory";
    }
}
