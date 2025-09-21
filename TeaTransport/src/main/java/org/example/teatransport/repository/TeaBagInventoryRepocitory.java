package org.example.teatransport.repository;

import org.example.teatransport.entity.TeaBagInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeaBagInventoryRepocitory extends JpaRepository<TeaBagInventory,String> {

}
