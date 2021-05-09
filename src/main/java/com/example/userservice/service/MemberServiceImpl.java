package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDTO;
import com.example.userservice.dto.ResponseMemberDTO;
import com.example.userservice.dto.ResponseOrder;
import com.example.userservice.entity.Member;
import com.example.userservice.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseMemberDTO createMember(CreateMemberDTO createMemberDTO){
        var member = modelMapper.map(createMemberDTO, Member.class);

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        var createdMember = memberRepository.save(member);

        return modelMapper.map(createdMember, ResponseMemberDTO.class);
    }

    @Override
    public ResponseMemberDTO findById(long memberId) {
        var member = memberRepository.findById(memberId).orElseGet(Member::new);

        if(member.getId() == 0)
            throw new UsernameNotFoundException("해당유저는 존재 하지 않습니다.");

        var responseMemberDTO = modelMapper.map(member, ResponseMemberDTO.class);

        List<ResponseOrder> orders = new ArrayList<>();

        responseMemberDTO.setOrders(orders);

        return responseMemberDTO;
    }

    @Override
    public List<ResponseMemberDTO> findAll() {
        List<Member> memberList = memberRepository.findAll();

        List<ResponseMemberDTO> responseMemberDTOList = new ArrayList<>();

        memberList.forEach(member -> responseMemberDTOList.add(modelMapper.map(member, ResponseMemberDTO.class)));

        return responseMemberDTOList;
    }
}
