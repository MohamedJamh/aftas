package com.aftas.repository;

import com.aftas.domain.Hunting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting, Long> {

    @Query("select h from Hunting h where h.competition.id = :competitionId and h.member.id = :memberId and h.fish.id = :fishId")
    Optional<Hunting> getHuntingsByCompetitionAndMemberAndFish(Long competitionId, Long memberId, Long fishId);
}
