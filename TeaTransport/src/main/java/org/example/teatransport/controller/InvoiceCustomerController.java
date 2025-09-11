package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.TeaBagInventoryDTO;
import org.example.teatransport.entity.Customer;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.CustomerRipocitory;
import org.example.teatransport.repository.InvoiceRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.example.teatransport.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin// Allow your frontend origin
@RestController
@RequestMapping("/invoiceCustomer")
@RequiredArgsConstructor

public class InvoiceCustomerController {
    private String date;
    private String custID;
    private final UserRepository userRepository;
    private final CustomerRipocitory customerRipocitory;
    private final InvoiceRipocitory invoiceRipocitory;
    private final CustomerService customerService;


    @GetMapping("/me")
        public ResponseEntity<String> setCustomerName() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();


            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));




            Customer customer = customerRipocitory.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

        System.out.println(customer.getId());
        custID = customer.getId();


            return ResponseEntity.ok(customer.getName());
        }

   @GetMapping("/date")
    public ResponseEntity<List<Integer>> getDate(@RequestBody TeaBagInventoryDTO teaBagInventoryDTO) {
        date = teaBagInventoryDTO.getDate();

       List<Integer> dto  = new ArrayList<>();
       dto = customerService.findByDateAndCustId(date,custID);

       return ResponseEntity.ok(dto);


   }

//   @GetMapping("/other")
//   public ResponseEntity<List<Double>> getOthers(@RequestBody TeaBagInventoryDTO teaBagInventoryDTO) {
////       date = teaBagInventoryDTO.getDate();
////
////
////       List<double> dto  = new ArrayList<>();
////       dto = customerService.findByDateAndCustId(date,custID);
////
////       return ResponseEntity.ok(dto);
//
//       List<Double> dto  = new ArrayList<>();
//       return dto;
//
//   }


}
