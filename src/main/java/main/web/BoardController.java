package main.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.service.BoardService;
import main.service.BoardVO;

@Controller
public class BoardController {
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	@RequestMapping("/boardWrite")
	public String boardWrite() {
		
		return "/board/boardWrite";
		
	}
	
	@RequestMapping("/boardWriteSave.do")
	@ResponseBody
	public String insertBoard(BoardVO vo) throws Exception {
		
		String result = boardService.insertBoard(vo);
		String msg = "";
		
		if(result == null) msg = "ok";
		else msg = "fail";

		return msg;
	}

}
