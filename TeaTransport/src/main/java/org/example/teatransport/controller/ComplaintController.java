package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.ComplaintDTO;
import org.example.teatransport.entity.Complaint;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.ComplaintRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.example.teatransport.service.ComplaintService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintRipocitory complaintRipocitory;
    private final ComplaintService complaintService;
    private final UserRepository userRepository;

    @PutMapping("/updateComplaint/{cmID}/{des}")
    public ResponseEntity<String> updateComplint(@PathVariable String cmID , @PathVariable String des){
        try {
            complaintService.updateComplaint(cmID,des);
            return ResponseEntity.ok("Complaint updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Update complaint");
        }
    }

    @DeleteMapping("/deleteComplaint/{id}")
    public ResponseEntity<String> deleteComplaint(@PathVariable String id) {
        try {
            complaintRipocitory.deleteById(id);
            return ResponseEntity.ok("Complaint deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting complaint");
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody ComplaintDTO complaint,
                                         @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        complaintService.saveComplaint(complaint, userDetails.getUsername());

        return ResponseEntity.ok("Complaint submitted with userId attached!");
    }

    @GetMapping("/LoadComplaint")
    public Page<?> LoadComplaint(Pageable pageable,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        return complaintService.findByUser(user, pageable);
    }



    @GetMapping("/getCountCom")
    public int[] getcontCom(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails){
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        List<Complaint> complaints = complaintRipocitory.findByUser(user);

        return complaintService.getcount(user);

    }

    @GetMapping("/LoadComplaintsAdmin")
    public  Page<?> LoadAllComplaints(Pageable pageable){
        return complaintService.findAll(pageable);

    }

    @PutMapping("/updateComplaintsAdmin")
    public String updateComplaitAdmin(@RequestBody ComplaintDTO complaint){
        complaintService.updateComplaintADMIN(complaint);

        return "Update Succsessfully!";
    }


}
