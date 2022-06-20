package com.warehouse.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_price")
public class ProductPrice {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    @JsonBackReference
    private Product product;

    @Column(scale = 2, nullable = false)
    private Double listPrice;

    @Column(scale = 2, nullable = false)
    private Double minPrice;
}
