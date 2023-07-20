package kr.co.jhta.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kr.co.jhta.model.BoardList;
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
		
		BoardList result = boardService.getBoards(param);
		
		model.addAttribute("result", result);
		
		return "board/list";
	}
	
	// 게시글 조회수 증가
	@GetMapping("/read")
	public String read(@RequestParam("no")int boardNo, Model model) {
		boardService.updateReadCount(boardNo);
		Board board = boardService.getBoardDetail(boardNo);
		model.addAttribute("board", board);
		return "board/detail";
	}
	
	// 게시글 삭제 요청과 매핑되는 요청핸들러 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String deleteBoard(@RequestParam("no") int boardNo, @AuthenticationPrincipal User user) {
		Board board = boardService.getBoardDetail(boardNo);
		boardService.deleteBoard(board, user);
		return "redirect:list";
	}
	
	// 게시글 수정 화면 요청과 매핑되는 요청핸들러 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify")
	public String modifyForm(@RequestParam("no") int boardNo, Model model) {
		Board board = boardService.getBoardDetail(boardNo);
		model.addAttribute("board", board);
		return "board/modifyform";
	}
	
	// 게시글 수정 요청과 매핑되는 요청핸들러 메소드
	@PostMapping("/modify")
	public String modify(@RequestParam("title") String title, @RequestParam("content")String content, @RequestParam("no") int boardNo,
						 @AuthenticationPrincipal User user) {
		Board savedBoard = boardService.getBoardDetail(boardNo);
		savedBoard.setTitle(title);
		savedBoard.setContent(content);
		boardService.updateBoard(savedBoard, user);
		return "redirect:list";
	}
	
}
