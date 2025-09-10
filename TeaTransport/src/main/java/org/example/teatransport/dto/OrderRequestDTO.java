package org.example.teatransport.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String custName;
    private String custAddress;
    private String custTel;
    private double totalPrice;
    private List<SellsDTO> cartItem;
}
