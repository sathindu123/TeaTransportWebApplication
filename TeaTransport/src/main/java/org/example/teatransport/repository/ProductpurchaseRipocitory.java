package org.example.teatransport.repository;

import org.example.teatransport.entity.ProductpurchseCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductpurchaseRipocitory extends JpaRepository<ProductpurchseCustomer,String> {

    @Query(value = "SELECT COALESCE(SUM(totalPrice), 0) " +
            "FROM productpurchasecustomer " +
            "WHERE custId = :custId " +
            "AND date BETWEEN :startDate AND :endDate AND productId = :MID", nativeQuery = true)
    Double getcountProductM(@Param("custId") String custId,
                            @Param("startDate") String startDate,
                            @Param("endDate") String endDate, String MID);

    @Query(value = "SELECT COALESCE(SUM(totalPrice), 0) " +
            "FROM productpurchasecustomer " +
            "WHERE custId = :custId " +
            "AND date BETWEEN :startDate AND :endDate AND productId = :MID", nativeQuery = true)
    Double getcountProductT(@Param("custId") String custId,
                            @Param("startDate") String startDate,
                            @Param("endDate") String endDate, String MID);


}
