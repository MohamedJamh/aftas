package com.aftas.service.impl;

import com.aftas.domain.Member;
import com.aftas.repository.MemberRepository;
import com.aftas.service.MemberService;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Override
    public Member createMember(Member member) {
        return null;
    }
}
