package org.example.teatransport.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "advance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advance {
    @Id
    @Column(name = "custId")
    private String custId ;
    private LocalDate date ;
    private String  month ;
    private double price ;
    @Column(name = "monthPrice")
    private double monthPrice ;

}

