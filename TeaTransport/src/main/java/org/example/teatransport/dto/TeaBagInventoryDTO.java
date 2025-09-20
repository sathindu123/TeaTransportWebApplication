package org.example.teatransport.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TeaBagInventoryDTO {

    private String custId;
    private LocalDate date;
    private int goldLeafAmount;
    private int goodLeafAmount;
}
