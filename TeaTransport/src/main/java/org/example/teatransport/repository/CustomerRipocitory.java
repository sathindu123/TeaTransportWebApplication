package org.example.teatransport.repository;

import org.example.teatransport.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRipocitory extends JpaRepository<Customer,String> {
//
//    @Query(value = "SELECT date, SUM(goldLeafAmount + goodLeafAmount) AS totalPrice " +
//            "FROM teabaginventory WHERE custId = :custId " +
//            "AND date BETWEEN ? AND ? GROUP BY date", nativeQuery = true)
//    List<Integer> findDailyTotalsByMonth(@Param("custID") String custID, @Param("date") String date);
}
