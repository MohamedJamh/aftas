package com.aftas.repository;

import com.aftas.domain.entities.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FishRepository extends JpaRepository<Fish, Long> {
    @Query("SELECT f FROM Fish f WHERE f.name = :name")
    Optional<Fish> findByName(String name);
}
