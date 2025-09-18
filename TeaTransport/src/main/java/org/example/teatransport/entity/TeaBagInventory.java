package org.example.teatransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "teabaginventory")
public class TeaBagInventory {
    @Id
    @Column(name = "custId")
    private String custId;
    private LocalDate date;
    @Column(name = "goldLeafAmount")
    private int goldLeafAmount;
    @Column(name = "goodLeafAmount")
    private int goodLeafAmount;


}
