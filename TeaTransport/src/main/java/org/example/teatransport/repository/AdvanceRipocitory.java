package org.example.teatransport.repository;

import org.example.teatransport.entity.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvanceRipocitory extends JpaRepository<Advance, String> {
    @Query(value = "SELECT COALESCE(SUM(monthPrice),0) FROM advance WHERE custId = :custId AND month = :month", nativeQuery = true)
    Double getAdvance(@Param("custId") String custId, @Param("month") String month);
}
