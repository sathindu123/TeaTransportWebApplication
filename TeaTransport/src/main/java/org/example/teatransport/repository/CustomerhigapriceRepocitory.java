package org.example.teatransport.repository;

import org.example.teatransport.entity.Customerhigaprice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerhigapriceRepocitory extends JpaRepository<Customerhigaprice, String> {

    @Query(value = "SELECT COALESCE(SUM(price),0) FROM customerhigaprice WHERE custId = :custId AND month = :date", nativeQuery = true)
    Double gethiga(String custId, String date);
}
