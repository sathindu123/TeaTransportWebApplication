package org.example.teatransport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customerhigaprice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customerhigaprice {
    @Id
    @Column(name = "custId")
    private String custId;
    private String month;
    private int price;

}
