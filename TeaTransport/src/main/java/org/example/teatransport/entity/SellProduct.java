package org.example.teatransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellProduct {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "u_id")
    private String uId;

    private String date;

    @Column(name = "product_id")
    private String productId;

    private int count;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "oID")
    private OrderReqDetails order;
}
