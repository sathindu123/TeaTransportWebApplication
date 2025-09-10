package org.example.teatransport.repository;

import org.example.teatransport.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRipocitory extends JpaRepository<Product, String> {
}
