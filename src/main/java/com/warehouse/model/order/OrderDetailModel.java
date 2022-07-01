package com.warehouse.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailModel {

    private Long articleNumber;
    private String name;
    private String description;
    private String category;
    private Long numberOfItem;
    private Double pricePerItem;

}
