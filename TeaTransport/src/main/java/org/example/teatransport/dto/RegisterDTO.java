package org.example.teatransport.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String id;
    private String username;
    private String password;
    private String role;
}
