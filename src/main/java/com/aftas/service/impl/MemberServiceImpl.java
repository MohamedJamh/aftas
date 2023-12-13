package com.aftas.service.impl;

import com.aftas.domain.Member;
import com.aftas.exception.ValidationException;
import com.aftas.repository.MemberRepository;
import com.aftas.service.CompetitionService;
import com.aftas.service.MemberService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;


@Component
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Override
    public Member createMember(Member member) throws ValidationException {
        Optional<Member> optionalMember = memberRepository.findByIdentityNumber(member.getIdentityNumber());
        if(optionalMember.isPresent())
            throw new ValidationException(new ErrorMessage("Member with identity number already exists"));
        Integer maxId = memberRepository.getMaxId();
        member.setAccessionDate(LocalDate.now());
        member.setNum( maxId + 1 + LocalDate.now().getYear());
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberIfExists(Long memberId) throws ValidationException {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty())
            throw new ValidationException(new ErrorMessage("Member not found"));
        return optionalMember.get();
    }

}
