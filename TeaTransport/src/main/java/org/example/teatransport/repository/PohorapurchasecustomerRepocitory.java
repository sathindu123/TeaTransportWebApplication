package org.example.teatransport.repository;

import org.example.teatransport.entity.Pohorapurchasecustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PohorapurchasecustomerRepocitory extends JpaRepository<Pohorapurchasecustomer,String> {

    @Query(value = "SELECT COALESCE(SUM(monthPrice),0) FROM pohorapurchasecustomer WHERE custId = :custId AND month = :month", nativeQuery = true)
    Double getcountPohora(String custId, String month);


    @Query("SELECT a.monthPrice FROM Pohorapurchasecustomer a " +
            "WHERE a.custId = :custId " +
            "AND (FUNCTION('STR_TO_DATE', a.month, '%M%Y') > FUNCTION('STR_TO_DATE', :date, '%M%Y'))")
    Double getnextpohora(String custId, String date);


}
