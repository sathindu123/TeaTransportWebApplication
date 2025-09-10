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
        System.out.println(orderId+" orderId "+productId+ " productID");
        try {
            int deletedProducts = ripocitory.deleteBy(orderId, productId);
            if (deletedProducts == 0) {
                System.out.println("error 1: No product found to delete with given orderId and productId");
                throw new RuntimeException("No product found to delete with given orderId and productId");
            }

            int deletedOrder = ripocitory.deleteByreq(orderId);
            if (deletedOrder == 0) {
                System.out.println("error 2: Order not found or could not be deleted");
                throw new RuntimeException("Order not found or could not be deleted");
            }
        } catch (Exception e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace(); // Full stack trace to see Hibernate SQL error
            throw new RuntimeException("Failed to delete order due to database error", e);
        }
    }



//    public List<Map<String, Object>> getOrderDetails(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//        return ripocitory.fetchOrderDetails(pageable);
//    }
}
