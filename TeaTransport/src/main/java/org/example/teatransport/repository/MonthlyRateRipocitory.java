package org.example.teatransport.repository;

import org.example.teatransport.entity.Monthlyrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MonthlyRateRipocitory extends JpaRepository<Monthlyrate,String> {

    @Query("SELECT c.goldrate FROM Monthlyrate c WHERE c.month = :newdate")
    Double getgoldRate(String newdate);

    @Query("SELECT c.goodrate FROM Monthlyrate c WHERE c.month = :newdate")
    Double getgoodRate(String newdate);

    @Query("SELECT COALESCE(SUM(c.goldLeafAmount + c.goodLeafAmount), 0) FROM TeaBagInventory c WHERE c.custId = :name AND c.date = :ss")
    int getCountTeaLeaf(String name, LocalDate ss);

    @Query("SELECT COALESCE(SUM(c.goldLeafAmount),0) " +
            "FROM TeaBagInventory c " +
            "WHERE c.custId = :id AND c.date BETWEEN :strartDate AND :endDate")
    int getTealEafCount(String id, LocalDate strartDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(c.goodLeafAmount),0) " +
            "FROM TeaBagInventory c " +
            "WHERE c.custId = :id AND c.date BETWEEN :strartDate AND :endDate")
    int getTealEafCount1(String id, LocalDate strartDate, LocalDate endDate);
}
