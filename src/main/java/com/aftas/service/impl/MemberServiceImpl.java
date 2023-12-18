package com.aftas.service.impl;

import com.aftas.domain.Member;
import com.aftas.exception.ValidationException;
import com.aftas.repository.MemberRepository;
import com.aftas.service.MemberService;
import java.time.LocalDate;
import java.util.List;
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
        if(maxId == null) maxId = 0;
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
    public Member getMemberIfExistsById(Long memberId) throws ValidationException {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty())
            throwNotFoundException();
        return optionalMember.get();
    }

    @Override
    public Member getMemberIfExistsByNumber(Integer memberCode) throws ValidationException {
        Optional<Member> optionalMember = memberRepository.findByNum(memberCode);
        if(optionalMember.isEmpty())
            throwNotFoundException();
        return optionalMember.get();
    }

    @Override
    public Page<Member> findByCriteria(String searchValue,Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        try {
            Integer memberNum = Integer.valueOf(searchValue);
            return memberRepository.findByNum(memberNum,paging);
        } catch (NumberFormatException e) {
            return memberRepository.findByFirstNameOrLastName(searchValue, searchValue, paging);
        }
    }

    private void throwNotFoundException() throws ValidationException {
        throw new ValidationException(new ErrorMessage("Member not found"));
    }

}
