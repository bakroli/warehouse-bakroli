package com.warehouse.service.component;

import org.springframework.stereotype.Component;

@Component
public class ProductComponent {

    public Long getLongValue(Long value) {
        if (value != null) {
            return value;
        } else {
            return 0L;
        }
    }

    public Double getDoubleValue(Double value) {
        if (value != null) {
            return value;
        } else {
            return 0D;
        }
    }

    public Boolean getBooleanValue(Boolean value) {
        if (value != null) {
            return true;
        } else {
            return false;
        }
    }

}
