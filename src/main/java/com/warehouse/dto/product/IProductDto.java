package com.warehouse.dto.product;

public interface IProductDto {

    Long getArticle_Number();
    String getName();
    String getDescription();
    String getProduct_Category_Prefix();
    Boolean getValid();
    Double getList_Price();
    Double getMin_Price();
    Long getStock();

}
