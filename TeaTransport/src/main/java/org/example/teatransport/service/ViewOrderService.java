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
            int deletedProducts = ripocitory.deleteBy(orderId, productId);
            if (deletedProducts == 0) {
                throw new RuntimeException("No product found to delete with given orderId and productId");
            }

            int deletedOrder = ripocitory.deleteByreq(orderId);
            if (deletedOrder == 0) {
                throw new RuntimeException("Order not found or could not be deleted");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Full stack trace to see Hibernate SQL error
            throw new RuntimeException("Failed to delete order due to database error", e);
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
