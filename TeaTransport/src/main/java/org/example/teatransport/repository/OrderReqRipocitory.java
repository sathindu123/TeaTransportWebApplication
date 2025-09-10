package org.example.teatransport.repository;

import org.example.teatransport.entity.OrderReqDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReqRipocitory extends JpaRepository<OrderReqDetails, String> {

}
