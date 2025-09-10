package org.example.teatransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReqDetails {
    @Id
    @Column(name = "oid")
    private String oID;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_address")
    private String custAddress;

    @Column(name = "cust_tel")
    private String custTel;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "uid")
    private String uID;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellProduct> sellProducts = new ArrayList<>();

}
