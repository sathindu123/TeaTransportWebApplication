package org.example.teatransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
    @Id
    private String complainId;
    @ManyToOne(fetch = FetchType.LAZY)  // Complaint එකකට එක user එකක්
    @JoinColumn(name = "user_id")       // Complaint table එකේ foreign key column
    private User user;
    private String description;
    private String status;
    private String remarks;
    private String date;

}
