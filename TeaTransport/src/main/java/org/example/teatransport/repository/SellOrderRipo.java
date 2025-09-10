package org.example.teatransport.repository;

import org.example.teatransport.entity.SellProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellOrderRipo extends JpaRepository<SellProduct,String> {

}
