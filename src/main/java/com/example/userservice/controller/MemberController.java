package com.example.userservice.controller;

import com.example.userservice.dto.CreateMemberDTO;
import com.example.userservice.dto.ResponseDTO;
import com.example.userservice.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/member-service")
public class MemberController {
    private final Environment environment;
    private final MemberService memberService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Member Service on Port "+ environment.getProperty("local.server.port");
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

    @GetMapping("/members")
    public ResponseEntity<Map<String, Object>> fetchMemberList(){
        var responseMemberList = memberService.findAll();

        var responseDto = new ResponseDTO("회원 리스트 조회 성공", responseMemberList).getResponseEntity();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Map<String, Object>> getMemberInformation(@PathVariable("id") long memberId){
        var responseMember = memberService.findById(memberId);

        var responseDto = new ResponseDTO("회원 조회 성공", responseMember).getResponseEntity();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }



}
