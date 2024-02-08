package com.aftas.repository;


import com.aftas.domain.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT p FROM Permission p WHERE p.subject = :subject AND p.action = :action")
    Optional<Permission> findBySubjectAndAction(String subject, String action);
}
