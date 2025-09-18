package org.example.teatransport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class ProductpurchseCustomer {
    @Id
    @Column(name = "custId")
    private String custId;
    private LocalDate date;
    @Column(name = "productId")
    private String  productId;
    private int quntity;
    @Column(name = "totalPrice")
    private double totalPrice;

}




