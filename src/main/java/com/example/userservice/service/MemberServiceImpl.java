package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDto;
import com.example.userservice.dto.ResponseMemberDto;
import com.example.userservice.dto.ResponseOrderDto;
import com.example.userservice.entity.Member;
import com.example.userservice.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var member = memberRepository.findByEmail(username).orElseGet(Member::new);
        if(member.getId() == 0)
            throw new UsernameNotFoundException(username);

        return new User(
               member.getEmail(),
               member.getPassword(),
               true,
               true,
               true,
               true,
               new ArrayList<>()
        );
    }

    @Override
    public ResponseMemberDto createMember(CreateMemberDto createMemberDTO){
        var member = modelMapper.map(createMemberDTO, Member.class);

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        var createdMember = memberRepository.save(member);

        return modelMapper.map(createdMember, ResponseMemberDto.class);
    }

    @Override
    public ResponseMemberDto findById(long memberId) {
        var member = memberRepository.findById(memberId).orElseGet(Member::new);

        if(member.getId() == 0)
            throw new UsernameNotFoundException("해당유저는 존재 하지 않습니다.");

        var responseMemberDTO = modelMapper.map(member, ResponseMemberDto.class);

        List<ResponseOrderDto> orders = new ArrayList<>();

        responseMemberDTO.setOrders(orders);

        return responseMemberDTO;
    }

    @Override
    public List<ResponseMemberDto> findAll() {
        List<Member> memberList = memberRepository.findAll();

        List<ResponseMemberDto> responseMemberDtoList = new ArrayList<>();

        memberList.forEach(member -> responseMemberDtoList.add(modelMapper.map(member, ResponseMemberDto.class)));

        return responseMemberDtoList;
    }
}
