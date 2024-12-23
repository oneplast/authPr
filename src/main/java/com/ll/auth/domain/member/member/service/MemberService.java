package com.ll.auth.domain.member.member.service;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.repository.MemberRepository;
import com.ll.auth.global.exceptions.ServiceException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public long count() {
        return memberRepository.count();
    }

    public Member join(String username, String password, String nickname) {
        findByUsername(username)
                .ifPresent(__ -> {
                    throw new IllegalArgumentException(
                            new ServiceException("400-1", "해당 username은 이미 사용중입니다."));
                });

        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .password2(UUID.randomUUID().toString())
                .build();

        return memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findById(long authorId) {
        return memberRepository.findById(authorId);
    }

    public Optional<Member> findByPassword2(String password2) {
        return memberRepository.findByPassword2(password2);
    }
}
