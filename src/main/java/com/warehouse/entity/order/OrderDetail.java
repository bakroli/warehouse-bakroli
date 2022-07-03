package com.warehouse.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.warehouse.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "details_row")
    private Long row;

    @ManyToOne
    @JoinColumn(name = "order_number")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "number_of_item")
    private Long numberOfItem;

    @Column(name = "price_per_item")
    private Double pricePerItem;

}
