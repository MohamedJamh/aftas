package com.aftas.service.impl;

import com.aftas.domain.Member;
import com.aftas.exception.ValidationException;
import com.aftas.repository.MemberRepository;
import com.aftas.service.MemberService;
import java.time.LocalDate;
import java.util.Optional;

import com.aftas.utils.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Member> getAllMembers(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return memberRepository.findAll(paging);
    }

    @Override
    public Member getMemberIfExists(Long memberId) throws ValidationException {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty())
            throw new ValidationException(new ErrorMessage("Member not found"));
        return optionalMember.get();
    }

}
