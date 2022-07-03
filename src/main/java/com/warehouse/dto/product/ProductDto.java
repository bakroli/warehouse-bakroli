package com.warehouse.dto.product;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @Positive
    private Long articleNumber;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    private String description;

    private Boolean valid;
    private String productCategory;
    private Double listPrice;
    private Double minPrice;

}
