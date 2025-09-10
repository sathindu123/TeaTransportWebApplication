package org.example.teatransport.dto;

import lombok.Data;

@Data
public class TeaBagInventoryDTO {
    private String id;
    private int qty;
    private String name;
    private String date;
    private int goldLeaf;
    private int goodLeaf;
}
