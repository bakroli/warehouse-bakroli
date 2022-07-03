package com.warehouse.controller.html;

import com.warehouse.model.product.ProductHistoryModel;
import com.warehouse.service.product.ProductHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductHtmlController {

    private final ProductHtmlService productHtmlService;

    @Autowired
    public ProductHtmlController(ProductHtmlService productHtmlService) {
        this.productHtmlService = productHtmlService;
    }

    @GetMapping("/products/html")
    public String getAllProductsDto(Model model) {
        model.addAttribute("products", productHtmlService.getAllProductModel());
        return "products";
    }

    @GetMapping("/products/html/{articleNumber}")
    public String getProductHistory(@PathVariable("articleNumber") Long articleNumber, Model model) {
        List<ProductHistoryModel> productHistory = productHtmlService.getProductHistoryModel(articleNumber);
        model.addAttribute("history", productHistory);
        model.addAttribute("product", productHtmlService.getProductModel(articleNumber));
        double lastPurchasePrice = productHtmlService.getLastPurchasePriceFromHistory(productHistory);
        model.addAttribute("lastpurchaseprice", lastPurchasePrice);
        return "producthistory";
    }

}
