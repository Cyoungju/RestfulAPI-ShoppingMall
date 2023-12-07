package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class HomeController {
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login"; // "login.html" 파일을 렌더링
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

}
