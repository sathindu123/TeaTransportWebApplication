package org.example.teatransport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurasheID implements Serializable {
    private String custId;
    private String productId;
}
