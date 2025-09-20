package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.entity.TeaBagInventory;
import org.example.teatransport.repository.TeaBagInventoryRepocitory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class EditReportController {

    private final TeaBagInventoryRepocitory teaBagInventoryRepocitory;

    @PostMapping("/submitTeaLeaf")
    public ResponseEntity<String> submit(@RequestBody TeaBagInventory teaBagInventory){
        TeaBagInventory nma =  teaBagInventoryRepocitory.save(teaBagInventory);

        return ResponseEntity.ok("successfully");
    }

}
