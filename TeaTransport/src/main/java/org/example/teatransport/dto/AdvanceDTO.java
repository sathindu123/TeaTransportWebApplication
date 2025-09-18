package org.example.teatransport.dto;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class AdvanceDTO {
    private String custId ;
    private LocalDate date ;
    private String  month ;
    private double price ;
    private double monthPrice ;
}
