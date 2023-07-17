package kr.co.jhta.web.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.jhta.service.UserService;
import kr.co.jhta.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	// 사용자 등록 화면 요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/register")
	public String registerForm() {
		
		return "user/registerform";
	}
	
	// 사용자 등록 요청과 매핑되는 요청핸들러 메소드
	@PostMapping("/register")
	public String register(String email, String password) {
		userService.createUser(email, password);
	
		return "redirect:/";
	}
	
	// 로그인 화면 요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/login")
	public String loginform() {
		return "user/loginform";
	}
	
	// 내정보 보기 화면 요청과 매핑되는 요청핸들러 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/info")
	public String info(@AuthenticationPrincipal User user, Model model) {
		log.info("user -> {}", user);
		
		model.addAttribute("user", user);
		
		return "user/info";
	}
	
	// 내정보 수정 화면 요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/modify")
	public String modifyform() {
		return "user/modifyform";
	}
	
	// 내정보 수정 요청과 매핑되는 요청핸들러 메소드
	@PostMapping("/modify")
	public String modify() {
		return "redirect:info";
	}

}
