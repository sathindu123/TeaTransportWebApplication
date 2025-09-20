package org.example.teatransport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaBagInventoryId implements Serializable {
    private String custId;
    private LocalDate date;
}
