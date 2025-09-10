package org.example.teatransport.repository;

import org.example.teatransport.entity.TeaBagInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRipocitory extends JpaRepository<TeaBagInventory, String> {

}
