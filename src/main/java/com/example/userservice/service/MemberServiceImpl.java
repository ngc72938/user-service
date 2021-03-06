package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.CreateMemberDto;
import com.example.userservice.dto.RequestLoginDto;
import com.example.userservice.dto.ResponseMemberDto;
import com.example.userservice.dto.ResponseOrderDto;
import com.example.userservice.entity.Member;
import com.example.userservice.entity.Role;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final JPAQueryFactory jpaQueryFactory;
    private final OrderServiceClient orderServiceClient;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseMemberDto createMember(CreateMemberDto createMemberDTO) {
        var member = modelMapper.map(createMemberDTO, Member.class);

        var savedMember = memberRepository.findByEmail(createMemberDTO.getEmail()).orElseGet(Member::new);
        if (savedMember.getId() != 0)
            throw new BusinessException("이미 사용중인 이메일 입니다.");

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles(Collections.singletonList(Role.CLIENT));

        var createdMember = memberRepository.save(member);

        return modelMapper.map(createdMember, ResponseMemberDto.class);
    }

    @Override
    public ResponseMemberDto findById(long memberId) {
        var member = memberRepository.findById(memberId).orElseGet(Member::new);

        if (member.getId() == 0)
            throw new UsernameNotFoundException("해당유저는 존재 하지 않습니다.");

        var responseMemberDTO = modelMapper.map(member, ResponseMemberDto.class);

        ResponseEntity<Map<String, Object>> orderMap = orderServiceClient.getOrders(member.getUserId());
        Map<String, Object> result = (Map<String, Object>) orderMap.getBody().get("payload");
        List<ResponseOrderDto> responseOrderList = (List<ResponseOrderDto>) result.get("data");

        responseMemberDTO.setOrders(responseOrderList);

        return responseMemberDTO;
    }

    @Override
    public List<ResponseMemberDto> findAll() {
        List<Member> memberList = memberRepository.findAll();

        List<ResponseMemberDto> responseMemberDtoList = new ArrayList<>();

        memberList.forEach(member -> responseMemberDtoList.add(modelMapper.map(member, ResponseMemberDto.class)));

        return responseMemberDtoList;
    }

    @Override
    public ResponseMemberDto loginMember(RequestLoginDto requestLoginDto) {
        var member = memberRepository.findByEmail(requestLoginDto.getEmail()).orElseGet(Member::new);
        member.verifyExist();

        if (!bCryptPasswordEncoder.matches(requestLoginDto.getPassword(), member.getPassword()))
            throw new BusinessException("회원정보를 확인해 주세요");

        return modelMapper.map(member, ResponseMemberDto.class);
    }
}
