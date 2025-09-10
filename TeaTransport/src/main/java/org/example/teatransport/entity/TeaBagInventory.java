package org.example.teatransport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeaBagInventory {
    @Id
    private String id;
    private int qty;
    private String name;
    private String date;
    private int goldLeaf;
    private int goodLeaf;


}
