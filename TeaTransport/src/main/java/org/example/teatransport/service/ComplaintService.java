package org.example.teatransport.service;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.ComplaintDTO;
import org.example.teatransport.entity.Complaint;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.ComplaintRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final UserRepository userRepository;
    private final ComplaintRipocitory complaintRipocitory;
    public void saveComplaint(ComplaintDTO complaint, String username) {

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Complaint com = Complaint.builder()
                .complainId(complaint.getComplainId() != null ? complaint.getComplainId() : UUID.randomUUID().toString()) // assign ID
                .description(complaint.getDescription())
                .status(complaint.getStatus())
                .remarks(complaint.getRemarks())
                .date(complaint.getDate())
                .user(user)
                .build();
        complaintRipocitory.save(com);

    }
}
