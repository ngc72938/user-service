package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDto;
import com.example.userservice.dto.RequestLoginDto;
import com.example.userservice.dto.ResponseMemberDto;

import java.util.List;

public interface MemberService {
    ResponseMemberDto createMember(CreateMemberDto createMemberDTO);

    ResponseMemberDto findById(long memberId);

    List<ResponseMemberDto> findAll();

    ResponseMemberDto loginMember(RequestLoginDto requestLoginDto);

}
