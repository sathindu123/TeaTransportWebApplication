package org.example.teatransport.repository;

import org.example.teatransport.entity.OrderReqDetails;
import org.example.teatransport.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderReqRipocitory extends JpaRepository<OrderReqDetails, String> {
    List<OrderReqDetails> findTop5ByCustNameContainingIgnoreCase(String custName);
}
