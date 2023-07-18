package kr.co.jhta.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.BoardDao;
import kr.co.jhta.dto.Pagination;
import kr.co.jhta.model.BoardList;
import kr.co.jhta.vo.Board;
import kr.co.jhta.vo.User;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	// 새 게시글 등록하기
	public void createBoard(Board board) {
		boardDao.insertBoard(board);
	}
	
	// 게시글 목록 조회하기(페이징처리 포함)
	public BoardList getBoards(Map<String, Object> param) {
		
		int totalRows = boardDao.getTotalRows(param);
		
		int page = (int) param.get("page");
		int rows = (int) param.get("rows");
		Pagination pagination = new Pagination(rows, page, totalRows);
		
		int begin = pagination.getBegin();
		int end = pagination.getEnd();
		
		param.put("begin", begin);
		param.put("end", end);
		
		BoardList result = new BoardList();
		List<Board> boards = boardDao.getBoards(param);
		
		result.setPagination(pagination);
		result.setBoards(boards);
		
		return result;
	}
	
	// 게시글 상세 조회하기
	public Board getBoardDetail(int boardNo) {
		Board board = boardDao.getBoardByNo(boardNo);
		board.setReadCount(board.getReadCount()+1);
		boardDao.updateBoard(board);
		return board;
	}
	
	public void deleteBoard(Board board, @AuthenticationPrincipal User user) {
		if (!board.getUser().getEmail().equals(user.getEmail())) {
			throw new IllegalArgumentException("해당 사용자는 게시판을 삭제할 권한이 없습니다.");
		}
		board.setDeleted("Y");
		boardDao.updateBoard(board);
	}
	
	// 게시글 정보 수정하기
	public void updateBoard(Board board) {
		boardDao.updateBoard(board);						
	}
}
