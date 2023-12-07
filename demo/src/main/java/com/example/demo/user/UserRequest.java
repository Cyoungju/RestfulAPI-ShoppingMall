package com.example.demo.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRequest {
    @Getter
    @Setter
    public static class JoinDTO {


        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;


        @NotEmpty
        @Size(min = 8, max = 20, message = "8자 이상 20자 이내로 작성 가능합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;

        //@NotEmpty
        private String username;

//        @Builder
//        public User toEntity(PasswordEncoder passwordEncoder) {
//            return User.builder()
//                    .email(email)
//                    .password(passwordEncoder.encode(password))
//                    .username(username)
//                    .phoneNumber(phoneNumber)
//                    .roles(Collections.singletonList("ROLE_USER"))
//                    .build();
//        }

        @Builder
        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }

    @NoArgsConstructor
    @Data
    public static class LoginDTO {
        private String email;
        private String password;

    }
}