package com.aftas.service;

import com.aftas.domain.Member;
import com.aftas.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface MemberService {
    Member createMember(Member member) throws ValidationException;
    Page<Member> getAllMembers(Integer pageNo, Integer pageSize);
    Member getMemberIfExists(Long memberId) throws ValidationException;
}
