package com.example.userservice.controller;

import com.example.userservice.dto.CreateMemberDto;
import com.example.userservice.dto.RequestLoginDto;
import com.example.userservice.dto.ResponseDto;
import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/v1")
public class MemberController {
    private final Environment environment;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Member Service on Port "
                + "port(local.server.port)=" +environment.getProperty("local.server.port")
                + " port(server.port)=" +environment.getProperty("server.port")
                + " token secret=" +environment.getProperty("spring.jwt.secret")
                + " gateway ip=" +environment.getProperty("gateway.ip");
    }

    @GetMapping("/welcome")
    public String welcome() {
        return environment.getProperty("greeting.message");
    }

    @PostMapping("/members")
    public ResponseEntity<Map<String, Object>> createMember(@RequestBody @Valid CreateMemberDto createMemberDTO) {
        var responseMember = memberService.createMember(createMemberDTO);

        var responseDto = new ResponseDto("회원가입 성공", responseMember).getResponseEntity();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/members")
    public ResponseEntity<Map<String, Object>> fetchMemberList(){
        var responseMemberList = memberService.findAll();

        var responseDto = new ResponseDto("회원 리스트 조회 성공", responseMemberList).getResponseEntity();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Map<String, Object>> getMemberInformation(@PathVariable("id") long memberId){
        var responseMember = memberService.findById(memberId);

        var responseDto = new ResponseDto("회원 조회 성공", responseMember).getResponseEntity();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> requestLogin(@RequestBody @Valid RequestLoginDto requestLoginDto, HttpServletResponse response){
        var responseMemberDto = memberService.loginMember(requestLoginDto);

        String token = jwtTokenProvider.createToken(responseMemberDto.getEmail(), responseMemberDto.getId(), responseMemberDto.getRoles());
        response.setHeader("Authorization", token);

        var responseDto = new ResponseDto("로그인 성공", responseMemberDto).getResponseEntity();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
