package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDto;
import com.example.userservice.dto.ResponseMemberDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {
    ResponseMemberDto createMember(CreateMemberDto createMemberDTO);

    ResponseMemberDto findById(long memberId);

    List<ResponseMemberDto> findAll();


}
