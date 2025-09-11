package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.ComplaintDTO;
import org.example.teatransport.entity.Complaint;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.ComplaintRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.example.teatransport.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintRipocitory complaintRipocitory;
    private final ComplaintService complaintService;
    private final UserRepository userRepository;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody ComplaintDTO complaint,
                                         @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        complaintService.saveComplaint(complaint, userDetails.getUsername());

        return ResponseEntity.ok("Complaint submitted with userId attached!");
    }

    @GetMapping("/LoadComplaint")
    public ResponseEntity<List<ComplaintDTO>> LoadComplaint(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {

        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null){
            return ResponseEntity.badRequest().build();
        }

        List<Complaint> complaints = complaintRipocitory.findByUser(user);

        System.out.println(complaints + "complaints loaded");

        List<ComplaintDTO> complaintDTOs = complaints.stream().map(c -> {
            ComplaintDTO dto = new ComplaintDTO();
            dto.setComplainId(c.getComplainId());
            dto.setDescription(c.getDescription());
            dto.setStatus(c.getStatus());
            dto.setRemarks(c.getRemarks());
            dto.setDate(c.getDate());
            return dto;
        }).collect(Collectors.toList());

        System.out.println(complaintDTOs + "sathindud sas");

        return ResponseEntity.ok(complaintDTOs);
    }




}
