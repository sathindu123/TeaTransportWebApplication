package org.example.teatransport.repository;

import jakarta.transaction.Transactional;
import org.example.teatransport.entity.Complaint;
import org.example.teatransport.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ComplaintRipocitory extends JpaRepository<Complaint, String> {
    List<Complaint> findByUser(Optional<User> user);

    @Modifying
    @Transactional
    @Query("UPDATE Complaint c SET c.description = :des WHERE c.complain_id = :cmID")
    void updateCopmalint(String cmID, String des);

    @Query(value = "SELECT COUNT(*) FROM complaint WHERE user_id = :userId", nativeQuery = true)
    int getAllCount(@Param("userId") String userId);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.user.id = :userId AND c.status = 'Pending'")
    int getPendingCount(@Param("userId") String userId);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.user.id = :userId AND c.status = 'In Progress'")
    int getInProgressCount(@Param("userId") String userId);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.user.id = :userId AND c.status = 'Resolved'")
    int getResolvedCount(@Param("userId") String userId);

    @Query("SELECT c FROM Complaint c WHERE c.user.id = :userId")
    Page<Complaint> findByUserComplaint(@Param("userId") String userId, Pageable pageable);


}
