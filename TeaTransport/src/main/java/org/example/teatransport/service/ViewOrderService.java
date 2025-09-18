package org.example.teatransport.service;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.repository.ViewOrderRipocitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ViewOrderService {

    private final ViewOrderRipocitory ripocitory;

    public Page<Map<String, Object>> getOrderDetails(Pageable pageable) {
        return ripocitory.fetchOrderDetails(pageable);
    }

    @Transactional
    public void deleteOrder(String orderId, String productId) {

        try {
            // 1. delete all products first
            ripocitory.deleteProductsByOrderId(orderId,productId);

            int count = ripocitory.orderIDCount(orderId);

            if (count == 0) {
                ripocitory.deleteOrderById(orderId);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage() + "sathinsu");
        }
    }

    public Page<Map<String, Object>> SearchOrderInputUser(String query, Pageable pageable) {
        return ripocitory.fetchOrderUserName(query,pageable);
    }


//    public List<Map<String, Object>> getOrderDetails(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//        return ripocitory.fetchOrderDetails(pageable);
//    }
}
