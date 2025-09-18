package org.example.teatransport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monthlyrate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monthlyrate {
    @Id
    private String month;
    private double goldrate;
    private double goodrate;
}
