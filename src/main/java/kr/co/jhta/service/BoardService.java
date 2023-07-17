package kr.co.jhta.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.BoardDao;
import kr.co.jhta.dto.Pagination;
import kr.co.jhta.vo.Board;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	// 새 게시글 등록하기
	public void createBoard(Board board) {
		boardDao.insertBoard(board);
	}
	
	// 게시글 목록 조회하기(페이징처리 포함)
	public List<Board> getBoards(Map<String, Object> param) {
		
		int totalRows = boardDao.getTotalRows(param);
		
		int page = (int) param.get("page");
		int rows = (int) param.get("rows");
		Pagination pagination = new Pagination(rows, page, totalRows);
		
		int begin = pagination.getBegin();
		int end = pagination.getEnd();
		
		param.put("begin", begin);
		param.put("end", end);
		
		List<Board> boards = boardDao.getBoards(param);
		
		return boards;
	}
	
	// 게시글 정보 수정하기
	public void updateBoard(Board board) {
		
	}
}
