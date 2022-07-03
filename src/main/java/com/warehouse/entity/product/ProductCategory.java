package com.warehouse.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @Column(unique = true, nullable = false)
    private String prefix;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "productCategory")
    @JsonBackReference
    private List<Product> products;

}
