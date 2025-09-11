package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.entity.OrderReqDetails;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.OrderReqRipocitory;
import org.example.teatransport.service.ViewOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class ViewOrder_ADMIN {

    private final ViewOrderService viewOrderService;
    private final OrderReqRipocitory orderReqRipocitory;

    @GetMapping("/viewOrder")
    public Page<Map<String, Object>> getOrders(Pageable pageable){
        return viewOrderService.getOrderDetails(pageable);
    }

    @DeleteMapping("/deleteOrder/{orderId}/{productId}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderId, @PathVariable String productId) {
        viewOrderService.deleteOrder(orderId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/LoadTableSudjest")
    public Page<Map<String, Object>> serachUserLoadTable(@RequestParam("query") String query, Pageable pageable){
        return viewOrderService.SearchOrderInputUser(query,pageable);
    }


    @GetMapping("/searchUser")
    public List<String> searchUsernames(@RequestParam String query) {
        return orderReqRipocitory.findTop5ByCustNameContainingIgnoreCase(query)
                .stream()
                .map(OrderReqDetails::getCustName)
                .toList();
    }

}
