package org.example.teatransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Productpurchasecustomer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ProductPurasheID.class)
public class ProductpurchseCustomer {
    @Id
    @Column(name = "custId")
    private String custId;
    private LocalDate date;
    @Id
    @Column(name = "productId")
    private String  productId;
    private int quntity;
    @Column(name = "totalPrice")
    private double totalPrice;

}




