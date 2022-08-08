package main.service;

import java.util.List;

public interface BoardService {
	/*
	 * 일반게시판 저장처리
	 */
	public String insertBoard(BoardVO vo) throws Exception;
	/*
	 * 일반게시판 화면 목록
	 */
	public List<?> selectBoardList(BoardVO vo) throws Exception;
	/*
	 * total 갯수 얻기  
	 */
	public int selectBoardTotal(BoardVO vo) throws Exception;

}
