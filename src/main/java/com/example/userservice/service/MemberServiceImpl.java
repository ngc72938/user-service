package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDTO;
import com.example.userservice.dto.ResponseMemberDTO;
import com.example.userservice.entity.Member;
import com.example.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseMemberDTO createMember(CreateMemberDTO createMemberDTO) {
        var member = modelMapper.map(createMemberDTO, Member.class);

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        var createdMember = memberRepository.save(member);

        return modelMapper.map(createdMember, ResponseMemberDTO.class);
    }
}
