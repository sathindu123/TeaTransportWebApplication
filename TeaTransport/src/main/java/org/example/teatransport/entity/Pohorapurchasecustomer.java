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
@Table(name = "pohorapurchasecustomer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pohorapurchasecustomer {
    @Id
    @Column(name = "custId")
    private String custId;
    private LocalDate date;
    @Column(name = "productId")
    private String productId;
    private int quntity;
    private String month;
    @Column(name = "totalPrice")
    private double totalPrice;
    @Column(name = "monthPrice")
    private double monthPrice;
}
