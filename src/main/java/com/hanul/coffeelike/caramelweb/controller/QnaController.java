package com.hanul.coffeelike.caramelweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;
import com.hanul.coffeelike.caramelweb.service.QnaService;

@Controller
public class QnaController {
	@Autowired
	private QnaService qnaService;
	
	//문의게시판 목록
	@RequestMapping("/qna")
	public String qna(HttpSession session,
					  Model model,
					  @RequestParam(required = false) String search,
					  @RequestParam(required = false) String keyword,
					  @RequestParam(defaultValue = "1") int currentPage) {
		//if(currentPage==null) currentPage = 0;
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setTotalCount(qnaService.totalCount());
		page.setSearch(search);
		page.setKeyword(keyword);

		int maximumPage = page.getMaximumPage(10);
			
		if(currentPage>maximumPage) {
			return "";
		}
		
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
		return "redirect:qna";
	}
		
	//문의글 상세화면 요청
	@RequestMapping("/detail.qna")
	public String detailQna(int id, Model model) {
		model.addAttribute("data", qnaService.detailQna(id));
		model.addAttribute("crlf", "\r\n");
		Page page = new Page();
		model.addAttribute("page", page);
		return "qna/detail";
	}
		
	//문의글 수정화면 요청
	@RequestMapping("modify.qna")
	public String deleteQna(Model model,
							@RequestParam int id) {
		model.addAttribute("data", qnaService.detailQna(id));
		return "qna/modify";
	}
		
	//문의글 수정저장 처리
	@RequestMapping("update.qna")
	public String updateQna(HttpSession session,
							Model model,
							Qna qna) {
		qnaService.updateQna(qna);
		model.addAttribute("id", qna.getId());
		model.addAttribute("url", "detail.qna");
		return "redirect:detail.qna";
	}
	
	//문의글 삭제처리
	@RequestMapping("delete.qna")
	public String deleteQna(HttpSession session,
							Model model,
							@RequestParam int id) {
		if(session.getAttribute("loginUser")==null) return "redirect:qna";
		
		qnaService.deleteQna(id);
		Page page = new Page();
		model.addAttribute("page", page);
		model.addAttribute("url", "qna");
		return "qna/redirect";
	}
}
