package com.warehouse.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    @NotNull
    @Positive
    private Long articleNumber;

    @NotNull
    @Positive
    private Long numberOfItem;

    @NotNull
    @Positive
    private Double pricePerItem;

}
