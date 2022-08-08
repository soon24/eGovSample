package main.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.service.BoardService;
import main.service.BoardVO;

// 컨트롤러와 연결되는 설정
@Service("boardService")
public class BoardServieImpl implements BoardService {

	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
	
	@Override
	public String insertBoard(BoardVO vo) throws Exception {
		return boardDAO.insertBoard(vo);
	}

	@Override
	public List<?> selectBoardList(BoardVO vo) throws Exception {
		return boardDAO.selectBoardList(vo);
	}

	@Override
	public int selectBoardTotal(BoardVO vo) throws Exception {
		return boardDAO.selectBoardTotal(vo);
	}
	@Override

	public BoardVO selectBoardDetail(int unq) throws Exception {
		return boardDAO.selectBoardDetail(unq);
	}

	@Override
	public int updateBoardHits(int unq) throws Exception {
		return boardDAO.updateBoardHits(unq);
	}
	
}
