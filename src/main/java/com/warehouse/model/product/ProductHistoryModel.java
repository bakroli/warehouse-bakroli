package com.warehouse.model.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistoryModel {

    private Long orderNumber;
    private String orderType;
    private LocalDate date;
    private String comment;
    private Long numberOfItem;
    private Double pricePerItem;
    private Double sum;
    private Long actualStock;

}
