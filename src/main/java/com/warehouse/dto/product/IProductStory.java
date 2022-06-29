package com.warehouse.dto.product;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

public interface IProductStory {

    Long getOrder_number();
    String getOrder_type();
    LocalDate getDate();
    String getComment();
    Long getNumber_of_item();
    Double getPrice_per_item();

}
