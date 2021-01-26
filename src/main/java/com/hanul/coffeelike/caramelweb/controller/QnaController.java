package com.hanul.coffeelike.caramelweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;
import com.hanul.coffeelike.caramelweb.data.QnaComment;
import com.hanul.coffeelike.caramelweb.service.QnaService;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;

@Controller
public class QnaController {
	public static final int QNA_PAGE_LIST_SIZE = 10;
	
	@Autowired
	private QnaService qnaService;
	@Autowired
	private UserService userService;
	
	//문의게시판 목록
	@RequestMapping("/list.qna")
	public String qna(Model model,
					  @RequestParam(required = false) @Nullable String search,
					  @RequestParam(required = false) @Nullable String keyword,
					  @RequestParam(defaultValue = "1") int currentPage) {
		Page page = new Page(qnaService.totalCount(search, keyword),
				currentPage,
				search,
				keyword,
				QNA_PAGE_LIST_SIZE);
		
		if(page.getCurrentPage()>page.getMaximumPage()) {
			return "";
		}
		
		page.setCurrentPage(currentPage);
		
		List<Qna> qnas = qnaService.getQna(page);
		model.addAttribute("page", page);
		model.addAttribute("qnas", qnas);
		return "qna/list";
	}
	
	
	
	//문의글쓰기 화면 요청
	@RequestMapping("/new.qna")
	public String writeQna() {
		return "qna/new";
	}
		
	//문의글쓰기 저장 처리 요청
	@RequestMapping("/insert.qna")
	public String insertQna(HttpSession session,
							Qna qna) {
		qnaService.insertQna(qna);
		return "redirect:list.qna";
	}
		
	//문의글 상세화면 요청
	@RequestMapping("/detail.qna")
	public String detailQna(HttpSession session,
							Model model,
							@RequestParam int id) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		boolean isAdmin = loginUser != null && userService.isAdmin(loginUser.getUserId());
		model.addAttribute("isAdmin", isAdmin);
		
		model.addAttribute("data", qnaService.detailQna(id));
		model.addAttribute("crlf", "\r\n");
		return "qna/detail";
	}
	
	
	//문의글 수정화면 요청
	@RequestMapping("/modify.qna")
	public String modifyQna(Model model,
							@RequestParam int id) {
		model.addAttribute("data", qnaService.detailQna(id));
		return "qna/modify";
	}
		
	//문의글 수정저장 처리
	@RequestMapping("/update.qna")
	public String updateQna(HttpSession session,
							Model model,
							Qna qna) {
		qnaService.updateQna(qna);
		model.addAttribute("id", qna.getId());
		model.addAttribute("url", "detail.qna");
		return "redirect:detail.qna";
	}
	
	//문의글 삭제처리
	@RequestMapping("/delete.qna")
	public String deleteQna(HttpSession session,
							Model model,
							@RequestParam int id) {
		if(session.getAttribute("loginUser")==null) return "redirect:list.qna";
		
		qnaService.deleteQna(id);
		
		return "redirect:list.qna";
	}
	
	//문의글 댓글 저장처리
	@ResponseBody
	@RequestMapping("/qna/comment/insert")
	public int insertQnaComment(HttpSession session,
								QnaComment qnaComment) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		
		if(loginUser == null) return -1;
		qnaComment.setWriter(loginUser.getUserId());

		return qnaService.insertQnaComment(qnaComment);
	}
	
	//문의글 댓글 목록조회 요청
	@RequestMapping("/qna/comment/{qnaId}")
	public String qnaCommentList(HttpSession session,
								 Model model,
								 @PathVariable int qnaId) {
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		boolean isAdmin = loginUser != null && userService.isAdmin(loginUser.getUserId());
		model.addAttribute("isAdmin", isAdmin);
		
		model.addAttribute("list", qnaService.qnaCommentList(qnaId));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		
		return "qna/comment/commentList";
	}
	
	//문의글 댓글 수정저장 처리
	@ResponseBody 
	@RequestMapping(value="qna/comment/update",
					produces="application/text; charset=utf-8")
	public String updateQnaComment(@RequestBody QnaComment qnaComment) {
		return qnaService.updateQnaComment(qnaComment) == 1 ? "성공!" : "실패!";
	}
	
	//문의글 댓글 삭제처리
	@ResponseBody
	@RequestMapping("/qna/comment/delete/{id}")
	public void deleteQnaComment(@PathVariable int id) {
		qnaService.deleteQnaComment(id);
	}
	
}
