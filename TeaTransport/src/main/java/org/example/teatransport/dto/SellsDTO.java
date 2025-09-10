package org.example.teatransport.dto;

import lombok.Data;

@Data
public class SellsDTO {
    private String custId;
    private String date;
    private String productId;
    private int count;
    private double price;
}
