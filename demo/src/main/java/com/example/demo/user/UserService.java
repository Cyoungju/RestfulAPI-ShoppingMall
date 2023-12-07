package com.example.demo.user;

import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception401;
import com.example.demo.core.error.exception.Exception500;
import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {

        Optional<User> byEmail = userRepository.findByEmail(joinDTO.getEmail());

        // 이미 존재하는 이메일인 경우 예외 처리
        if (byEmail.isPresent()) {
            throw new Exception400("이미 존재하는 이메일입니다: " + joinDTO.getEmail());
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(joinDTO.getPassword());
        joinDTO.setPassword(encodedPassword);

        userRepository.save(joinDTO.toEntity());
    }

    @Transactional
    public String login(UserRequest.LoginDTO requestDTO) {
        try {
            // 디버깅을 위한 로그
            System.out.println("Trying to authenticate user: " + requestDTO.getEmail());

            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            // 인증을 시도하고, 성공하면 Authentication 객체를 받아옴
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 디버깅을 위한 로그
            System.out.println("Authentication successful.");

            // 인증 완료된 Principal을 가져옴
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            // 디버깅을 위한 로그
            System.out.println("User details retrieved: " + customUserDetails.getUsername());

            // JWT 토큰 생성 및 반환
            String token = JwtTokenProvider.create(customUserDetails.getUser());

            // 디버깅을 위한 로그
            System.out.println("JWT token created: " + token);

            return token;

        } catch (Exception e) {
            // 로그인 실패시 401 예외 발생
            System.out.println("Authentication failed: " + e.getMessage());
            throw new Exception401("인증되지 않음.");
        }
    }


    public void checkEmail(String email){
        // 동일한 이메일이 있는지 확인.
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()) {
            throw new Exception400("이미 존재하는 이메일 입니다. : " + email);
        }
    }

}
