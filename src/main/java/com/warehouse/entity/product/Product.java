package com.warehouse.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.warehouse.entity.order.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_number", unique = true, nullable = false)
    private Long articleNumber;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean valid;

    @ManyToOne
    @JoinColumn(name = "product_category_prefix")
    private ProductCategory productCategory;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ProductPrice productPrice;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<OrderDetail> orderDetail;

    @Transient
    private Long actualStock;

}
