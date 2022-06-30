package com.warehouse.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    private Long articleNumber;
    private String name;
    private String description;
    private String category;
    private Boolean valid;
    private Double listPrice;
    private Double minPrice;
    private Long stock;

}
