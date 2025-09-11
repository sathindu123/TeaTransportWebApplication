package org.example.teatransport.repository;

import org.example.teatransport.entity.Complaint;
import org.example.teatransport.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRipocitory extends JpaRepository<Complaint, String> {
    List<Complaint> findByUser(Optional<User> user);
}
