package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.ApiResponse;
import org.example.teatransport.dto.AuthDTO;
import org.example.teatransport.dto.AuthResponseDTO;
import org.example.teatransport.dto.RegisterDTO;
import org.example.teatransport.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin // Allow your frontend origin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
            @RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "user registered succesfully",
                        authService.register(registerDTO)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthDTO authDTO){
        AuthResponseDTO authResponse = authService.authenticate(authDTO);
        return ResponseEntity.ok(new ApiResponse(200,
                "OK",authService.authenticate(authDTO)));

    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

        // Sample data
        data.put("message", "Welcome to your dashboard!");
        data.put("totalCustomers", 120);
        data.put("totalOrders", 350);
        data.put("revenue", 756000);

        return ResponseEntity.ok(data);
    }




}

