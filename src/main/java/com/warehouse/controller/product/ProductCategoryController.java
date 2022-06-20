package com.warehouse.controller.product;

import com.warehouse.dto.product.ProductCategoryDto;
import com.warehouse.entity.product.ProductCategory;
import com.warehouse.service.product.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Product Category")
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    @Operation(summary = "List all product category.")
    @ApiResponse(responseCode = "200", description = "Get list successful")
    public List<ProductCategoryDto> getAllCategories() {
        return productCategoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<String> addNewProductCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("INVALID DATA");
        } else {
            productCategoryService.addNewProductCategory(productCategoryDto);
            return ResponseEntity.ok().body("Save new category: " + productCategoryDto.getPrefix());
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a prefix.")
    @ApiResponse(responseCode = "200", description = "Delete prefix successful")
    @ApiResponse(responseCode = "400", description = "Fail to delete")
    public ResponseEntity<String> deleteProductCategory(@RequestParam(value = "prefix") String prefix) {
        try {
            productCategoryService.deleteProductCategory(prefix);
            return ResponseEntity.ok().body("DELETING prefix: " + prefix);
        } catch(EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("No Such Prefix!");
        } catch (DataIntegrityViolationException ce) {
            return ResponseEntity.badRequest().body("Cannot be deleted! It is in use.");
        }
    }

    @PutMapping
    public void updateProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryService.updateProductCategory(productCategoryDto);
    }

    @GetMapping("/{prefix}")
    public ProductCategory getProductCategoryByPrefix(@PathVariable("prefix") String prefix) {
        return productCategoryService.getProductCategoryByPrefix(prefix);
    }



}
