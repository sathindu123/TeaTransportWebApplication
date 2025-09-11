package org.example.teatransport.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ComplaintDTO {
    private String complainId;
    private String userId;
    private String description;
    private String status;
    private String remarks;
    private String date;

}
