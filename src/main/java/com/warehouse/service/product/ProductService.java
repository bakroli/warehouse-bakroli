package com.warehouse.service.product;

import com.warehouse.dto.product.*;
import com.warehouse.entity.product.Product;
import com.warehouse.entity.product.ProductCategory;
import com.warehouse.entity.product.ProductPrice;
import com.warehouse.repository.order.OrderDetailRepository;
import com.warehouse.repository.product.ProductCategoryRepository;
import com.warehouse.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    // GET ALL PRODUCTS

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        Map<Long, Long> stocksMap = getAllStocks();
        for(Product product : products) {
            Long stock = stocksMap.get(product.getId());
            if (stock == null) {
                stock = 0L;
            }
            product.setActualStock(stock);
        }
        return products;
    }

    private Map<Long, Long> getAllStocks() {
        List<IStock> stocks = orderDetailRepository.countProductStock();
        Map<Long, Long> stocksMap = new TreeMap<>();
        for (IStock iStock : stocks) {
            stocksMap.put(iStock.getPid(), iStock.getStock());
        }
        return stocksMap;
    }

    // GET PRODUCT BY ARTICLE NUMBER

    public Product getProductByArticleNumber(Long articleNumber) {
        Product product = productRepository.findByArticleNumber(articleNumber);
        Long productStock = getProductStock(product.getId());
        product.setActualStock(productStock);
        return product;
    }

    private Long getProductStock(Long id) {
        Long productStock = orderDetailRepository.getProductStock(id);
        if (productStock == null) {
            productStock = 0L;
        }
        return productStock;
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
        product.setValid(setValidFromDto(productDto.getValid()));

        ProductPrice productPrice = new ProductPrice();
        productPrice.setProduct(product);
        productPrice.setListPrice(setPriceFromDto(productDto.getListPrice()));
        productPrice.setMinPrice(setPriceFromDto(productDto.getMinPrice()));
        product.setProductPrice(productPrice);

        examinePrice(product.getProductPrice());

        product.setProductCategory(setProductCategoryFromDto(productDto.getProductCategory()));

        return product;
    }

    private Double setPriceFromDto(Double price) {
        if (price == null) {
            return 0D;
        } else {
            return price;
        }
    }

    private Boolean setValidFromDto(Boolean valid) {
        if (valid == null) {
            return false;
        } else {
            return true;
        }
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

    // GET ALL I-PRODUCT-DTO

    public List<IProductDto> getAllIProductDto() {
        return productRepository.getAllProductDto();
    }

    // GET ALL PRODUCT-DTO

    public List<ProductDtoGet> getAllProductDto() {
        List<IProductDto> iProductDtoList = productRepository.getAllProductDto();
        return createIDtoToDto(iProductDtoList);
    }

    private List<ProductDtoGet> createIDtoToDto(List<IProductDto> iProductDtoList) {
        List<ProductDtoGet> productDtoGetList = new ArrayList<>();
        for(IProductDto iProductDto : iProductDtoList) {
            ProductDtoGet productDtoGet = new ProductDtoGet();
            productDtoGet.setArticleNumber(iProductDto.getArticle_Number());
            productDtoGet.setName(iProductDto.getName());
            productDtoGet.setDescription(iProductDto.getDescription());
            productDtoGet.setCategory(iProductDto.getProduct_Category_Prefix());
            productDtoGet.setValid(iProductDto.getValid());
            productDtoGet.setListPrice(iProductDto.getList_Price());
            productDtoGet.setMinPrice(iProductDto.getMin_Price());
            if (iProductDto.getStock() != null) {
                productDtoGet.setStock(iProductDto.getStock());
            } else {
                productDtoGet.setStock(0L);
            }
            productDtoGetList.add(productDtoGet);
        }
        return productDtoGetList;
    }

    // GET I-PRODUCT-HISTORY

    public List<IProductStory> getIProductHistory(Long articleNumber) {
        List<IProductStory> iProductStories = orderDetailRepository.getProductHistory(articleNumber);
        return iProductStories;
    }
}
