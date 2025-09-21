package org.example.teatransport.entity;


import jakarta.persistence.*;
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
@IdClass(AdvanceID.class)
public class Advance {
    @Id
    @Column(name = "custId")
    private String custId ;
    private LocalDate date ;
    @Id
    private String  month ;
    private double price ;
    @Column(name = "monthPrice")
    private double monthPrice ;

}

