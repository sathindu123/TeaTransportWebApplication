package org.example.teatransport.repository;

import jakarta.transaction.Transactional;
import org.example.teatransport.entity.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AdvanceRipocitory extends JpaRepository<Advance, String> {
    @Query(value = "SELECT COALESCE(SUM(monthPrice),0) FROM advance WHERE custId = :custId AND month = :month", nativeQuery = true)
    Double getAdvance(@Param("custId") String custId, @Param("month") String month);

    @Transactional
    @Modifying
    @Query("delete from Advance a where a.custId = :id AND a.date = :date")
    int deleteAdvance(String id, LocalDate date);
}
