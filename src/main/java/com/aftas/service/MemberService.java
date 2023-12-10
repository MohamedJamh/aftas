package com.aftas.service;

import com.aftas.domain.Member;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    Member createMember(Member member) throws ValidationException;

    List<Member> getAllMembers();
}
