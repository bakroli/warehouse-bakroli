package com.warehouse.service.product;

import com.warehouse.dto.product.*;
import com.warehouse.entity.product.Product;
import com.warehouse.entity.product.ProductCategory;
import com.warehouse.entity.product.ProductPrice;
import com.warehouse.model.dao.StockDao;
import com.warehouse.repository.order.OrderDetailRepository;
import com.warehouse.repository.product.ProductCategoryRepository;
import com.warehouse.repository.product.ProductRepository;
import com.warehouse.service.component.ProductComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductComponent productComponent;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository productCategoryRepository,
                          OrderDetailRepository orderDetailRepository,
                          ProductComponent productComponent) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productComponent = productComponent;
    }

    // GET ALL PRODUCTS

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        Map<Long, Long> stocksMap = getAllStocks();
        for (Product product : products) {
            Long stock = stocksMap.get(product.getId());
            product.setActualStock(productComponent.getLongValue(stock));
        }
        return products;
    }

    private Map<Long, Long> getAllStocks() {
        List<StockDao> stocks = orderDetailRepository.countProductStock();
        Map<Long, Long> stocksMap = new TreeMap<>();
        for (StockDao iStock : stocks) {
            stocksMap.put(iStock.getPid(), iStock.getStock());
        }
        return stocksMap;
    }

    // GET PRODUCT BY ARTICLE NUMBER

    public Product getProductByArticleNumber(Long articleNumber) {
        Product product = productRepository.findByArticleNumber(articleNumber);
        Long productStock = orderDetailRepository.getProductStock(product.getId());
        product.setActualStock(productComponent.getLongValue(productStock));
        return product;
    }

    // SAVE NEW PRODUCT

    public void saveNewProduct(ProductDto productDto) {
        Product product = convertProductDtoToEntity(productDto);
        productRepository.save(product);
    }

    private Product convertProductDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setArticleNumber(productDto.getArticleNumber());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setValid(productComponent.getBooleanValue(productDto.getValid()));
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProduct(product);
        productPrice.setListPrice(productComponent.getDoubleValue(productDto.getListPrice()));
        productPrice.setMinPrice(productComponent.getDoubleValue(productDto.getMinPrice()));
        product.setProductPrice(productPrice);
        examinePrice(product.getProductPrice());
        product.setProductCategory(setProductCategoryFromDto(productDto.getProductCategory()));
        return product;
    }

    private void examinePrice(ProductPrice productPrice) {
        if (productPrice.getListPrice() < productPrice.getMinPrice()) {
            throw new IllegalArgumentException();
        }
    }

    private ProductCategory setProductCategoryFromDto(String category) {
        if (category == null) {
            return null;
        } else {
            return productCategoryRepository.findById(category).orElse(null);
        }
    }

    // UPDATE PRODUCT

    public void updateProductByArticleNumber(ProductDto productDto) {
        Product product = getValidProduct(productDto);
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
        if (productDto.getProductCategory() != null) {
            ProductCategory productCategory = productCategoryRepository.findById(productDto.getProductCategory()).orElse(null);
            if (productCategory != null) {
                product.setProductCategory(productCategory);
            }
        }
        if (productDto.getDescription() != null) {
            product.setDescription(setUpdateDescription(productDto.getDescription(), product.getDescription()));
        }
        if (productDto.getListPrice() != null && productDto.getListPrice() >= 0) {
            product.getProductPrice().setListPrice(productDto.getListPrice());
        }
        if (productDto.getMinPrice() != null && productDto.getMinPrice() >= 0) {
            product.getProductPrice().setMinPrice(productDto.getMinPrice());
        }
        examinePrice(product.getProductPrice());
        productRepository.save(product);
    }

    private Product getValidProduct(ProductDto productDto) {
        if (productDto.getArticleNumber() == null) {
            throw new IllegalArgumentException();
        }
        Product product = productRepository.findByArticleNumber(productDto.getArticleNumber());
        if (product == null) {
            throw new IllegalArgumentException();
        }
        return product;
    }

    private String setUpdateDescription(String newDescription, String oldDescription) {
        System.out.println(newDescription.isEmpty());
        if (!newDescription.equals("") && !newDescription.isBlank()) {
            return newDescription;
        } else {
            return oldDescription;
        }
    }

    // DELETE PRODUCT

    public void deleteProductByArticleNumber(Long articleNumber) {
        Long id = productRepository.findByArticleNumber(articleNumber).getId();
        productRepository.deleteById(id);
    }

}

