package com.example.userservice.service;

import com.example.userservice.dto.CreateMemberDTO;
import com.example.userservice.dto.ResponseMemberDTO;

public interface MemberService {
    ResponseMemberDTO createMember(CreateMemberDTO createMemberDTO);
}
