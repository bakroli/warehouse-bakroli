package com.warehouse.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto{

    @NotNull @NotBlank @NotEmpty
    @Size(min = 2, max = 8)
    @Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9-]{0,6}[a-zA-Z0-9]")
    @Schema(description = "Product category prefix", example = "PS")
    private String prefix;

    @NotNull @NotBlank @NotEmpty
    @Size(min = 2, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9-]{0,18}[a-zA-Z0-9]")
    @Schema(description = "Product category name", example = "PopeScope")
    private String name;

}
