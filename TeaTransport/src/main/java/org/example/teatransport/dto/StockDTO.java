package org.example.teatransport.dto;

import lombok.Data;

@Data
public class StockDTO {
    private String id;
    private String type;  // Fixed spelling from 'cetegory' to 'category'
    private int count;
    private double price;
}
