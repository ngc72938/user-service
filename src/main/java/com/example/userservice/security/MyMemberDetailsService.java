package com.example.userservice.security;

import com.example.userservice.entity.Member;
import com.example.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var member = memberRepository.findByEmail(email).orElseGet(Member::new);

        return new User(
                member.getEmail(),
                member.getPassword(),
                member.getAuthorities()
        );
    }
}
