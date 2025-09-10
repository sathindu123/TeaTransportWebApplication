package org.example.teatransport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(length = 60)
    private String id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
