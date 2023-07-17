package kr.co.jhta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.service.BoardService;
import kr.co.jhta.vo.Board;
import kr.co.jhta.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// 새 게시글 등록 화면 요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/register")
	public String registerForm() {
		
		return "board/form";
	}
	// 새 게시글 등록 요청과 매핑되는 요청핸들러 메소드
	
	@PostMapping("/register")
	public String register(Board board, @AuthenticationPrincipal User user) {
		board.setUser(user);
		boardService.createBoard(board);
		return "redirect:list";
	}
	
	// 최신 게시글 목록(가장 최근에 등록된 게시글 10개)요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/list")
	public String list(@RequestParam(name = "rows", required = false, defaultValue = "10") int rows,
					   @RequestParam(name = "page", required = false, defaultValue = "1") int page,
					   Model model) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rows", rows);
		param.put("page", page);
		
		List<Board> boards = boardService.getBoards(param);
		
		model.addAttribute("boards", boards);
		
		return "board/list";
	}
	
	// 게시글 상세정보 화면 요청과 매핑되는 요청핸들러 메소드
	
	// 게시글 삭제 요청과 매핑되는 요청핸들러 메소드
	
	// 게시글 수정 화면 요청과 매핑되는 요청핸들러 메소드
	
	
	
}
