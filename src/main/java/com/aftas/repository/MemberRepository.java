package com.aftas.repository;

import com.aftas.domain.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentityNumber(String identityNumber);
    Optional<Member> findByNum(Integer num);

    @Query("SELECT m FROM Member m where m.id IN (SELECT r.member.id FROM Ranking r where r.competition.id = :competitionId)")
    List<Member> findAllByCompetition(Long competitionId);

    @Query("SELECT MAX(m.id) FROM Member m")
    Integer getMaxId();

    Page<Member> findByNum(Integer memberNum, Pageable pageable);

    Page<Member> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);
}
