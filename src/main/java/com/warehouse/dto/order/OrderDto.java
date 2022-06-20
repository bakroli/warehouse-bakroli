package com.warehouse.dto.order;

import com.warehouse.entity.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private LocalDate date;

    @NotBlank
    private String comment;

    @NotNull
    private OrderType orderType;

    @NotNull
    private List<OrderDetailDto> orderDetails;
}
