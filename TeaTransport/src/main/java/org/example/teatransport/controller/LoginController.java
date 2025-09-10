package org.example.teatransport.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class LoginController {
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Hello admin";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "Hello user";
    }
    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customer() {
        return "Hello customer";
    }
}
