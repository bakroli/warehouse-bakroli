package com.warehouse.service.product;

import com.warehouse.model.dao.ProductDao;
import com.warehouse.model.dao.ProductHistoryDao;
import com.warehouse.model.product.ProductModel;
import com.warehouse.model.product.ProductHistoryModel;
import com.warehouse.repository.order.OrderDetailRepository;
import com.warehouse.repository.product.ProductRepository;
import com.warehouse.service.component.ProductComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductHtmlService {

    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductComponent productComponent;

    @Autowired
    public ProductHtmlService(ProductRepository productRepository,
                              OrderDetailRepository orderDetailRepository,
                              ProductComponent productComponent) {
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productComponent = productComponent;
    }

    // GET PRODUCT-DAO

    public ProductModel getProductModel(Long articleNumber) {
        ProductDao ProductDao = productRepository.getProductDaoByArticleNumber(articleNumber);
        return createDaoToModel(ProductDao);
    }

    // GET ALL PRODUCT-DAO

    public List<ProductModel> getAllProductModel() {
        List<ProductDao> productDaoList = productRepository.getAllProductDao();
        return createDaoToModelList(productDaoList);
    }

    private List<ProductModel> createDaoToModelList(List<ProductDao> iProductDtoList) {
        List<ProductModel> productDtoGetList = new ArrayList<>();
        for(ProductDao iProductDto : iProductDtoList) {
            ProductModel productDtoGet = createDaoToModel(iProductDto);
            productDtoGetList.add(productDtoGet);
        }
        return productDtoGetList;
    }

    private ProductModel createDaoToModel(ProductDao productDao) {
        ProductModel productModel = new ProductModel();

        productModel.setArticleNumber(productDao.getArticle_Number());
        productModel.setName(productDao.getName());
        productModel.setDescription(productDao.getDescription());
        productModel.setCategory(productDao.getProduct_Category_Prefix());
        productModel.setValid(productDao.getValid());
        productModel.setListPrice(productDao.getList_Price());
        productModel.setMinPrice(productDao.getMin_Price());
        productModel.setStock(productComponent.getLongValue(productDao.getStock()));

        return productModel;
    }

    // GET PRODUCT-HISTORY

    public List<ProductHistoryModel> getProductHistoryModel(Long articleNumber) {
        return createProductHistoryDaoToModel(orderDetailRepository.getIProductHistory(articleNumber));
    }

    private List<ProductHistoryModel> createProductHistoryDaoToModel(List<ProductHistoryDao> productHistoryDaos) {
        List<ProductHistoryModel> productHistories = new ArrayList<>();
        long actualStock = 0L;
        for (ProductHistoryDao productHistoryDao : productHistoryDaos) {
            ProductHistoryModel productHistoryModel = new ProductHistoryModel();
            productHistoryModel.setOrderNumber(productHistoryDao.getOrder_number());
            productHistoryModel.setOrderType(productHistoryDao.getOrder_type());
            productHistoryModel.setDate(productHistoryDao.getDate());
            productHistoryModel.setComment(productHistoryDao.getComment());
            productHistoryModel.setPricePerItem(productHistoryDao.getPrice_per_item());
            Long stock = productComponent.getLongValue(productHistoryDao.getNumber_of_item());
            productHistoryModel.setNumberOfItem(stock);
            if (productHistoryDao.getOrder_type().equals("IN")) {
                actualStock += stock;
            } else {
                actualStock -= stock;
            }
            productHistoryModel.setActualStock(actualStock);
            productHistoryModel.setSum(stock * productHistoryDao.getPrice_per_item());
            productHistories.add(productHistoryModel);
        }
        return productHistories;
    }

    public double getLastPurchasePriceFromHistory(List<ProductHistoryModel> productHistory) {
        double lastPurchasePrice = 0;
        for (int i = 0; i < productHistory.size(); i++) {
            if (productHistory.get(productHistory.size() - i - 1).getOrderType().equals("IN")) {
                lastPurchasePrice = productHistory.get(productHistory.size() - i - 1).getPricePerItem();
                break;
            }
        }
        return lastPurchasePrice;
    }

}



