package com.aftas.repository;

import com.aftas.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    Optional<User> findByIdentityNumber(String identityNumber);
    Optional<User> findByNum(Integer num);

    @Query("SELECT u FROM User u where u.id IN (SELECT r.user.id FROM Ranking r where r.competition.id = :competitionId)")
    List<User> findAllByCompetition(Long competitionId);

    @Query("SELECT MAX(u.id) FROM User u")
    Integer getMaxId();

    Page<User> findByNum(Integer memberNum, Pageable pageable);

    Page<User> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);

    @Query("SELECT u FROM User u where u.isEnable = false")
    Page<User> getDisabledUsers(Pageable pageable);
}
