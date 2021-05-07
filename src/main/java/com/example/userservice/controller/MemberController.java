package com.example.userservice.controller;

import com.example.userservice.dto.CreateMemberDTO;
import com.example.userservice.dto.ResponseDTO;
import com.example.userservice.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/")
public class MemberController {
    private final ModelMapper modelMapper;
    private final Environment environment;
    private final MemberService memberService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return environment.getProperty("greeting.message");
    }

    @PostMapping("/members")
    public ResponseEntity<Map<String, Object>> createMember(@RequestBody @Valid CreateMemberDTO createMemberDTO) {
        var responseMember = memberService.createMember(createMemberDTO);

        var responseDto = new ResponseDTO("회원가입 성공", responseMember).getResponseEntity();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
