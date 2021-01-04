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
	public String Qna(HttpSession session,
					  Model model,
				      @Nullable @RequestParam(required = false) Integer index) {
		if(index==null) index=0;
			
		Page qna = new Page();
		qna.setTotalCount(qnaService.totalCount());
		int maximumPage = qna.getMaximumPage(10);
			
		if(index>=maximumPage) {
			return "";
		}
			
		qna.setCurrentPage(index);
			
		List<Object> qnas = qnaService.getQna(qna);
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
		qnaService.qna_insert(qna);
		return "redirect:qna";
	}
		
	//문의글 상세화면 요청
	@RequestMapping("/detail.qna")
	public String detailQna(int id, Model model) {
		model.addAttribute("data", qnaService.qna_detail(id));
		model.addAttribute("crlf", "\r\n");
			
		return "qna/detail";
	}
		
	//문의글 수정화면 요청
	//제목 공백 글자 뒤에 제거되는 문제가 있답니다...
	@RequestMapping("modify.qna")
	public String deleteQna(Model model,
							@RequestParam int id) {
		model.addAttribute("data", qnaService.qna_detail(id));
		return "qna/modify";
	}
		
	//문의글 수정저장 처리
	@RequestMapping("update.qna")
	public String updateQna(HttpSession session,
							Model model,
							Qna qna) {
		qnaService.qna_update(qna);
		model.addAttribute("id", qna.getId());
		model.addAttribute("url", "detail.qna");
		return "redirect:detail.qna";
	}
}
