package org.example.teatransport.repository;

import org.apache.catalina.mapper.Mapper;
import org.example.teatransport.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRipocitory extends JpaRepository<Customer,String> {
    Optional<Customer> findByName(String name);

    @Query("select c.id from Customer c where c.name = :name")
    String findByIds(String name);

    @Query("select c.name from Customer c where c.id = :name")
    String findByNames(String name);


//    @Query("SELECT COALESCE(SUM(a.monthPrice), 0) FROM Advance a WHERE a.custId = :custId AND a.month = :month")
//    Double getadvance(@Param("custId") String custId, @Param("month") String month);

//
//    @Query(value = "SELECT date, SUM(goldLeafAmount + goodLeafAmount) AS totalPrice " +
//            "FROM teabaginventory WHERE custId = :custId " +
//            "AND date BETWEEN ? AND ? GROUP BY date", nativeQuery = true)
//    List<Integer> findDailyTotalsByMonth(@Param("custID") String custID, @Param("date") String date);
}
