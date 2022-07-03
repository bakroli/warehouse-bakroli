package com.warehouse.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @NotNull
    @PastOrPresent
    private LocalDate date;

    private String comment;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[I][N]$|^[O][U][T]$")
    private String orderType;

    @NotNull
    private List<OrderDetailDto> orderDetails;

}
