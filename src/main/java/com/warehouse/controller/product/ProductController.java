package com.warehouse.controller.product;

import com.warehouse.dto.product.ProductDto;
import com.warehouse.entity.product.Product;
import com.warehouse.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product operations")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "List all product")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{articleNumber}")
    @Operation(summary = "List one product by article number")
    public ResponseEntity<Product> getProductByArticleNumber(@PathVariable("articleNumber") Long articleNumber) {
        try {
            return ResponseEntity.ok(productService.getProductByArticleNumber(articleNumber));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> saveNewProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("INVALID DATA");
        }
        try {
            Long articleNumber = productService.saveNewProduct(productDto);
            return ResponseEntity.ok().body("NEW Product save OK, product article number: " + articleNumber);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR new product save");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductByArticleNumber(@RequestParam(value = "articleNumber") Long articleNumber) {
        try {
            productService.deleteProductByArticleNumber(articleNumber);
            return ResponseEntity.ok().body("DELETING");
        } catch(EmptyResultDataAccessException ee) {
            return ResponseEntity.badRequest().body("INVALID ID");
        } catch (DataIntegrityViolationException ce) {
            return ResponseEntity.badRequest().body("NEM TÖRÖLHETŐ! KAPCSOLATBAN VAN. TEDD FALSRA HA NEM KELL");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateProductByArticleNumber(@RequestBody ProductDto productDto) {
        try {
            productService.updateProductByArticleNumber(productDto);
            return ResponseEntity.ok().body("Update ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update wrong");
        }
    }

}
