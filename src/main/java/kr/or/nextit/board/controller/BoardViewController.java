/*package kr.or.nextit.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.board.model.Board;
import kr.or.nextit.board.service.BoardService;
import kr.or.nextit.board.service.impl.BoardServiceImpl;
import kr.or.nextit.web.servlet.Controller;

public class BoardViewController implements Controller{
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String boSeqNo = request.getParameter("bo_seq_no");
		
		Board board = null;
		
		if(boSeqNo != null) {
			
			int bo_seq_no = Integer.parseInt(boSeqNo);
			BoardService boardService = BoardServiceImpl.getInstance();			
			board = boardService.getBoard(bo_seq_no);
		}
		
		request.setAttribute("board", board);
		
		return "board/boardView";
	}
}
















*/