package com.example.demo.user;

import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception401;
import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.security.JwtTokenProvider;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error){

        // ** 동일한 email이 존재 하는지 확인.
        Optional<User> byEmail = userRepository.findByEmail(requestDTO.getEmail());

        // ** 존재 한다면 error 발생
        if(byEmail.isPresent()){
            throw new Exception400("이미 존재 하는 Email입니다."+ requestDTO.getEmail());
        }

        // ** password 인코딩
        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        requestDTO.setPassword(encodedPassword);

        // ** 저장
        userRepository.save(requestDTO.toEntity());
        return ResponseEntity.ok(ApiUtils.success(null));
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error){
        String jwt = "";

        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());
            //사용자의 이메일과 패스워드를 포함한 인증 토큰을 생성합
            Authentication authentication = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );
            //사용자가 제공한 이메일과 비밀번호로 사용자를 인증하려고 하는것
            // ** 인증 완료 값을 받아온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

            // ** 토큰 발급 - 이 JWT 토큰은 사용자 인증을 통해 확인된 사용자의 정보를 포함

            jwt =  JwtTokenProvider.create(customUserDetails.getUser());
        }catch (Exception e){
            // 401 반환.
            throw new Exception401("인증되지 않음.");
        }

        System.out.println(jwt);

        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));

        //jwt이 HEADER로 들어가 있어야함
    }
}
