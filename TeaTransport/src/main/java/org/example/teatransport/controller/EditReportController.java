package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.entity.Advance;
import org.example.teatransport.entity.ProductpurchseCustomer;
import org.example.teatransport.entity.TeaBagInventory;
import org.example.teatransport.repository.AdvanceRipocitory;
import org.example.teatransport.repository.ProductpurchaseRipocitory;
import org.example.teatransport.repository.TeaBagInventoryRepocitory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class EditReportController {

    private final TeaBagInventoryRepocitory teaBagInventoryRepocitory;
    private final AdvanceRipocitory advanceRipocitory;
    private final ProductpurchaseRipocitory productpurchaseRipocitory;

    @PostMapping("/submitTeaLeaf")
    public ResponseEntity<String> submit(@RequestBody TeaBagInventory teaBagInventory){
        TeaBagInventory nma =  teaBagInventoryRepocitory.save(teaBagInventory);

        return ResponseEntity.ok("successfully");
    }

    @DeleteMapping("/DeleteTeaLeaf")
    public ResponseEntity<String> Delete(@RequestBody TeaBagInventory teaBagInventory){
       try {
           teaBagInventoryRepocitory.delete(teaBagInventory);
           return ResponseEntity.ok("successfully");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error: " + e.getMessage());
       }

    }

    @PostMapping("/SubmitAdvance")
    public ResponseEntity<String> submitAdvance(@RequestBody Advance advance){
        advanceRipocitory.save(advance);

        return ResponseEntity.ok("successfully");
    }

    @DeleteMapping("/deleteAdvance")
    public ResponseEntity<String> deleteAdvance(@RequestBody Advance advance){
        String id = advance.getCustId();
        LocalDate date = advance.getDate();
        try {
            int deleted = advanceRipocitory.deleteAdvance(id, date);

            if (deleted > 0) {
                return ResponseEntity.ok("Deleted Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Advance found for customer ID: " + id + " and date: " + date);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/submitTeaPacket")
    public ResponseEntity<String> submitTeaPacket(@RequestBody ProductpurchseCustomer productpurchseCustomer){
        try {
            productpurchaseRipocitory.save(productpurchseCustomer);
            return ResponseEntity.ok("submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while saving: " + e.getMessage());
        }
    }

}
