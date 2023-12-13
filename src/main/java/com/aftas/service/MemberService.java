package com.aftas.service;

import com.aftas.domain.Member;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    Member createMember(Member member) throws ValidationException;
    List<Member> getAllMembers();
    Member getMemberIfExists(Long memberId) throws ValidationException;
}
