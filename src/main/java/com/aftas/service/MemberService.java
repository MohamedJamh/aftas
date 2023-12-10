package com.aftas.service;

import com.aftas.domain.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Member createMember(Member member);
}
