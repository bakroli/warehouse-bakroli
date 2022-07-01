package com.warehouse.controller.product;

import com.warehouse.dto.product.ProductDto;
import com.warehouse.entity.product.Product;
import com.warehouse.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product operations")
public class ProductController {

    private final ProductService productService;

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
    public ResponseEntity<?> getProductByArticleNumber(@PathVariable("articleNumber") Long articleNumber) {
        try {
            return ResponseEntity.ok(productService.getProductByArticleNumber(articleNumber));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid article number: " + articleNumber + " !");
        }
    }

    @PostMapping
    public ResponseEntity<String> saveNewProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("INVALID DATA");
        }
        try {
            productService.saveNewProduct(productDto);
            return ResponseEntity.ok().body("NEW Product save OK, product article number: " + productDto.getArticleNumber());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR new product save");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateProductByArticleNumber(@RequestBody ProductDto productDto) {
        try {
            productService.updateProductByArticleNumber(productDto);
            return ResponseEntity.ok().body("Updating, product article number: " + productDto.getArticleNumber());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update wrong");
        }
    }

    @DeleteMapping("/{articleNumber}")
    public ResponseEntity<String> deleteProductByArticleNumber(@PathVariable("articleNumber") Long articleNumber) {
        try {
            productService.deleteProductByArticleNumber(articleNumber);
            return ResponseEntity.ok().body("Delete");
        } catch(NullPointerException ne) {
            return ResponseEntity.badRequest().body("Invalid Article No");
        } catch (DataIntegrityViolationException ce) {
            return ResponseEntity.badRequest().body("Cannot be Deleted");
        }
    }

//    @GetMapping("/dto")
//    public List<IProductGet> getAllProductDto() {
//        return productService.getAllIProductDto();
//    }
//
//    @GetMapping("/history/{articleNumber}")
//    public List<IProductHistory> getProductHistory(@PathVariable("articleNumber") Long articleNumber) {
//        return productService.getIProductHistory(articleNumber);
//    }
//
//    @GetMapping("/dto/{articleNumber}")
//    public ProductGetDto getProductDto(@PathVariable("articleNumber") Long articleNumber) {
//        return productService.getProductDtoGet(articleNumber);
//    }

}
