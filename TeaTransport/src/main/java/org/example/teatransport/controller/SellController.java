package org.example.teatransport.controller;


import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.ApiResponse;
import org.example.teatransport.dto.OrderRequestDTO;
import org.example.teatransport.dto.SellsDTO;
import org.example.teatransport.dto.TeaBagInventoryDTO;
import org.example.teatransport.entity.Customer;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.SellRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.example.teatransport.service.SellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin // Allow your frontend origin
public class SellController {

    private String uID;
    private final SellRipocitory sellRipocitory;
    private final UserRepository userRepository;
    private final SellService service;

    @GetMapping("/setDetailsProduct")
    public ResponseEntity<ApiResponse> setDetailsProduct() {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "succesfully",
                        sellRipocitory.findAll()
                )
        );
    }

    @PostMapping("/addtocart")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();


            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            service.createOrder(user, orderRequestDTO);

            response.put("status", "success");
            response.put("message", "Order placed successfully!");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }




}
