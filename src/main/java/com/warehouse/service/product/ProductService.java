package com.warehouse.service.product;

import com.warehouse.dto.product.ProductDto;
import com.warehouse.entity.product.Product;
import com.warehouse.entity.product.ProductCategory;
import com.warehouse.entity.product.ProductPrice;
import com.warehouse.repository.order.OrderDetailRepository;
import com.warehouse.repository.product.ProductCategoryRepository;
import com.warehouse.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository productCategoryRepository,
                          OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        for(Product product: products) {
            Long productStock = orderDetailRepository.getProductStock(product.getId());
            if (productStock != null) {
                product.setActualStock(productStock);
            } else {
                product.setActualStock(0L);
            }
        }
        return products;
    }

    public Product getProductByArticleNumber(Long articleNumber) {
        Product product = productRepository.findByArticleNumber(articleNumber);
        Long id = product.getId();
        Long productStock = orderDetailRepository.getProductStock(id);
        if (productStock == null) {
            productStock = 0L;
        }
        product.setActualStock(productStock);
        return product;
    }

    public Long saveNewProduct(ProductDto productDto) {
        Product product = convertProductDtoToEntity(productDto);
        if (product.getProductPrice().getListPrice() == null) {
            product.getProductPrice().setListPrice(0D);
        }
        if (product.getProductPrice().getMinPrice() == null) {
            product.getProductPrice().setMinPrice(0D);
        }
        if (product.getProductPrice().getListPrice() < product.getProductPrice().getMinPrice()) {
            throw new IllegalArgumentException();
        }

        productRepository.save(product);
        return product.getArticleNumber();
    }

    private Product convertProductDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setArticleNumber(productDto.getArticleNumber());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setValid(productDto.getValid());
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProduct(product);
        productPrice.setListPrice(productDto.getListPrice());
        productPrice.setMinPrice(productDto.getMinPrice());
        product.setProductPrice(productPrice);
        ProductCategory productCategory = productCategoryRepository.findById(productDto.getProductCategory()).orElse(null);
        product.setProductCategory(productCategory);
        return product;
    }

    public void deleteProductByArticleNumber(Long articleNumber) {
        Long id = productRepository.findByArticleNumber(articleNumber).getId();
        productRepository.deleteById(id);
    }

    public void updateProductByArticleNumber(ProductDto productDto) {
        Long articleNumber = productDto.getArticleNumber();
        if (articleNumber == null) {
            throw new IllegalArgumentException();
        }
        Product product = productRepository.findByArticleNumber(articleNumber);
        if (product == null) {
            throw new IllegalArgumentException();
        }

        if (productDto.getValid() != null) {
            if (productDto.getValid() == Boolean.FALSE) {
                Long productStock = orderDetailRepository.getProductStock(product.getId());
                if (productStock != null && productStock > 0) {
                    throw new IllegalArgumentException();
                }
                product.setValid(Boolean.FALSE);
            } else {
                product.setValid(Boolean.TRUE);
            }
        }

        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }

        if (productDto.getListPrice() != null) {
            product.getProductPrice().setListPrice(productDto.getListPrice());
        }

        if (productDto.getMinPrice() != null) {
            product.getProductPrice().setMinPrice(productDto.getMinPrice());
        }

        if (product.getProductPrice().getListPrice() < product.getProductPrice().getMinPrice()) {
            throw new IllegalArgumentException();
        }

        productRepository.save(product);
    }
}
