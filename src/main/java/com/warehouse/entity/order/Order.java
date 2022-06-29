package com.warehouse.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Long orderNumber;

    @Column(nullable = false)
    private LocalDate date;

    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<OrderDetail> orderDetails;
}
