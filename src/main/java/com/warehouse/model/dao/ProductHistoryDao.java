package com.warehouse.model.dao;

import java.time.LocalDate;

public interface ProductHistoryDao {

    Long getOrder_number();
    String getOrder_type();
    LocalDate getDate();
    String getComment();
    Long getNumber_of_item();
    Double getPrice_per_item();

}
