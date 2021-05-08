package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDTO;
import com.example.userservice.dto.ResponseMemberDTO;

import java.util.List;

public interface MemberService {
    ResponseMemberDTO createMember(CreateMemberDTO createMemberDTO);

    ResponseMemberDTO findById(long memberId);

    List<ResponseMemberDTO> findAll();
}
