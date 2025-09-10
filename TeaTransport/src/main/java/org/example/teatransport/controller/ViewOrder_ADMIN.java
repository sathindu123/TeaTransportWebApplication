package org.example.teatransport.controller;

import lombok.RequiredArgsConstructor;
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
@CrossOrigin // Allow your frontend origin
public class ViewOrder_ADMIN {
    private final ViewOrderService viewOrderService;

    @GetMapping("/viewOrder")
    public Page<Map<String, Object>> getOrders(Pageable pageable){
        return viewOrderService.getOrderDetails(pageable);
    }

    @DeleteMapping("/deleteOrder/{orderId}/{productId}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderId, @PathVariable String productId) {
        viewOrderService.deleteOrder(orderId, productId);
        return ResponseEntity.ok().build();
    }



}
