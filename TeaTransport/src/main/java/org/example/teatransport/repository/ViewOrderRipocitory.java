package org.example.teatransport.repository;

import jakarta.transaction.Transactional;
import org.example.teatransport.entity.SellProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface ViewOrderRipocitory extends JpaRepository<SellProduct, String> {
    @Query("SELECT new map(sp.productId as productId, sp.count as count, " +
            "ord.custName as custName, ord.custAddress as custAddress, ord.custTel as custTel, ord.oID as oid) " +
            "FROM SellProduct sp " +
            "JOIN sp.order ord " +
            "ORDER BY sp.id DESC, ord.oID DESC")
    Page<Map<String, Object>> fetchOrderDetails(Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM SellProduct sp WHERE sp.order.oID = :id AND sp.productId = :productId")
    int deleteBy(String id, String productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderReqDetails sp WHERE sp.oID = :id")
    int deleteByreq(String id);

}
