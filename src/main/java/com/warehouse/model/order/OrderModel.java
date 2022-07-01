package com.warehouse.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {

    private Long orderNumber;
    private LocalDate date;
    private String comment;
    private String orderType;

}
