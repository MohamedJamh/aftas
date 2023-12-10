package com.aftas.repository;

import com.aftas.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentityNumber(String identityNumber);
}
