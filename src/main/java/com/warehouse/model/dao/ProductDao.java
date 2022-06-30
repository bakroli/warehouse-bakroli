package com.warehouse.model.dao;

public interface ProductDao {

    Long getArticle_Number();
    String getName();
    String getDescription();
    String getProduct_Category_Prefix();
    Boolean getValid();
    Double getList_Price();
    Double getMin_Price();
    Long getStock();

}
