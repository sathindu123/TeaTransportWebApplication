package org.example.teatransport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvanceID implements Serializable {
    private String custId;
    private String month;
}
