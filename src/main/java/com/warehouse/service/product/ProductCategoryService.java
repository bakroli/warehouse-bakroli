package com.warehouse.service.product;

import com.warehouse.dto.product.ProductCategoryDto;
import com.warehouse.entity.product.ProductCategory;
import com.warehouse.repository.product.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryService {

    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtoList = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            productCategoryDtoList.add(convertProductCategoryEntityToDto(productCategory));
        }
        return productCategoryDtoList;
    }


    private ProductCategoryDto convertProductCategoryEntityToDto(ProductCategory productCategory) {
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setPrefix(productCategory.getPrefix());
        productCategoryDto.setName(productCategory.getName());
        return productCategoryDto;
    }


    public void addNewProductCategory(ProductCategoryDto productCategoryDto) {
        productCategoryRepository.save(convertProductCategoryDtoToEntity(productCategoryDto));
    }

    private ProductCategory convertProductCategoryDtoToEntity(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setPrefix(productCategoryDto.getPrefix());
        productCategory.setName(productCategoryDto.getName());
        return productCategory;
    }

    public void deleteProductCategory(String prefix) {
        productCategoryRepository.deleteById(prefix);
    }


    public void updateProductCategory(ProductCategoryDto productCategoryDto) {
        productCategoryRepository.save(convertProductCategoryDtoToEntity(productCategoryDto));
    }


    public ProductCategory getProductCategoryByPrefix(String prefix) {
        return productCategoryRepository.findById(prefix).orElse(null);
        //return productCategoryRepository.findByPrefix(prefix);
    }
}
