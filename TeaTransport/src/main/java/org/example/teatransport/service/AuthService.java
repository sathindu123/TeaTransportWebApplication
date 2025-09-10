package org.example.teatransport.service;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.AuthDTO;
import org.example.teatransport.dto.AuthResponseDTO;
import org.example.teatransport.dto.RegisterDTO;
import org.example.teatransport.entity.Role;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.CustomerRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.example.teatransport.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    private CustomerRipocitory customerRepository;


    public AuthResponseDTO authenticate(AuthDTO authDTO){
        // validate credentials
        User user=userRepository.findByUsername(authDTO.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        // check password
        if (!passwordEncoder.matches(
                authDTO.getPassword(),
                user.getPassword())){
            throw new BadCredentialsException("Invalid credentials");
        }
        // generate token
        String token = jwtUtil.generateToken(authDTO.getUsername());

        // user.getRole() යන එකෙන් role ගන්නවා (ඔයාගේ User entity එකේ role field එක තිබ්බා කියලා හිතමු)
        return new AuthResponseDTO(token, user.getRole().name());
    }

    // register user
    public String register(RegisterDTO registerDTO) {
        // 1️⃣ Username check
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String userId;

        // 2️⃣ Customer check
        if ("CUSTOMER".equalsIgnoreCase(registerDTO.getRole())) {
            boolean exists = customerRepository.existsById(registerDTO.getId());
            if (!exists) {
                throw new RuntimeException("Customer ID does not exist in the customer table");
            }
            userId = registerDTO.getId(); // use the provided Customer ID
        } else {
            // 3️⃣ Other roles: auto-generate ID
            userId = "U" + System.currentTimeMillis();
        }

        // 4️⃣ Build and save user
        User user = User.builder()
                .id(userId)
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

}
