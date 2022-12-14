package main.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	@RequestMapping("/boardList.do")
	public String selectBoardList(BoardVO vo, ModelMap model) throws Exception {

		int unit = 5;
		
		// 총 데이터 개수
		int total = boardService.selectBoardTotal(vo);
		
		// 12/10 -> 1.2 -> 2
		int totalPage = (int) Math.ceil((double)total/unit);
		
		int viewPage = vo.getViewPage();
		
		if (viewPage > totalPage || viewPage < 1) {
			viewPage = 1;
		}
		
		// 1 -> 1,10 // 2 -> 11, 20 // 3 ->21,30
		int startIndex = (viewPage - 1) * unit + 1;
		int endIndex = startIndex + (unit - 1);
		
		// total -> 34 (4page)
		// 1p -> 34~25, 2p -> 24~15, 3p -> 14~5, 4p -> 4~1
		/* int p1 = total - 0;
		 int p2 = total -10; 
		 int p3 = total -20;
		 int p4 = total -30; */
		
		int startRowNo = total - (viewPage-1)*unit;
		
		vo.setStartIndex(startIndex);
		vo.setEndIndex(endIndex);
		
		List<?> list = boardService.selectBoardList(vo);
		// System.out.println("total : "+total);
		// System.out.println("totalPage : "+totalPage);
		// System.out.println("rowNumber : "+startRowNo);
		
		model.addAttribute("rowNumber", startRowNo);
		model.addAttribute("total", total);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("resultList", list);
		
		return "board/boardList";
	}
	
	@RequestMapping("/boardDetail.do")
	public String selectBoardDetail(BoardVO vo, ModelMap model) throws Exception {
		
		/*
		 * 조회수 증가
		 */
		boardService.updateBoardHits(vo.getUnq());
		
		/*
		 * 상세보기
		 */
		BoardVO boardVO = boardService.selectBoardDetail(vo.getUnq());
		
		// \n을 br tag로 변환
		String content = boardVO.getContent();
		boardVO.setContent(content.replace("\n", "<br>"));
		
		model.addAttribute("boardVO", boardVO);
		
		return "board/boardDetail";
	}	

	@RequestMapping("/boardModifyWrite.do")
	public String selectBoardModifyWrite(BoardVO vo, ModelMap model) throws Exception {

		//System.out.println("boardModifyWrite.do ==> 1");
		BoardVO boardVO = boardService.selectBoardDetail(vo.getUnq());
		model.addAttribute("boardVO", boardVO);
		
		return "board/boardModifyWrite";

	}

	@RequestMapping("/boardModifySave.do")
	@ResponseBody
	public String updateBoard(BoardVO vo) throws Exception {

		int result = 0 ;
		
		//System.out.println("count ==> Start");
		int count = boardService.selectBoardPass(vo);  // int count = 1;
		//System.out.println("count ==> "+count);
		if (count == 1) {
			result = boardService.updateBoard(vo); // result = 1;
		} else {
			result = -1;
		}
		return result+""; // ajax jsp에 전송 시에는 반드시 String 형태로 return 해야됩니다.
	}

	@RequestMapping("/passWrite.do")
	public String passWrite(int unq, ModelMap model) {
		
		System.out.println("passWrite 1==> ");
		model.addAttribute("unq", unq);
		
		return "board/passWrite";
	}
	
	@RequestMapping("/boardDelete.do")
	@ResponseBody //ajax에 결과 값을 전달(비동기화 방식)하기위한 annotation
	public String deleteBoard(BoardVO vo) throws Exception {
		
		int result = 0;
		/* 
		 * 암호  일치 검사 count = 1; 일치 count = 0; 일치하지 않음.
		 */
		int count = boardService.selectBoardPass(vo);  // int count = 1;
		
		if (count == 1) {
			result = boardService.deleteBoard(vo);
		} else if(count == 0) {
			result = -1;
		}
		
		return result+""; // ajax jsp에 전송 시에는 반드시 String 형태로 return 해야됩니다.
	}	
	
}
