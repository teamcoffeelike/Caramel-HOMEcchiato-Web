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
import com.hanul.coffeelike.caramelweb.service.NoticeService;
import com.hanul.coffeelike.caramelweb.data.Qna;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/notice")
	public String notice(HttpSession session,
			Model model,
			@Nullable @RequestParam(required = false) Integer index) {
		if(index==null) index = 0;
		
		Page notice = new Page();
		notice.setTotalCount(noticeService.totalCount());
		int maximumPage = notice.getMaximumPage(10);
		
		if(index>=maximumPage) {
			// 인덱스 초과, 특수 처리
			return "123";
		}
		
		notice.setCurrentPage(index);
		
		// service로 넘김
		List<Object> notices = noticeService.getNotice(notice);
		model.addAttribute("notices", notices);
		return "notice/list";
	}
	
	//문의게시판 목록
	@RequestMapping("/qna")
	public String Qna(HttpSession session,
					  Model model,
					  @Nullable @RequestParam(required = false) Integer index) {
		if(index==null) index=0;
		
		Page qna = new Page();
		qna.setTotalCount(noticeService.totalCount());
		int maximumPage = qna.getMaximumPage(10);
		
		if(index>=maximumPage) {
			return "";
		}
		
		qna.setCurrentPage(index);
		
		List<Object> qnas = noticeService.getQna(qna);
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
		noticeService.qna_insert(qna);
		return "redirect:qna";
	}
	
	//문의글 상세화면 요청
	@RequestMapping("/detail.qna")
	public String detailQna(int id, Model model) {
		model.addAttribute("data", noticeService.qna_detail(id));
		model.addAttribute("crlf", "\r\n");
		
		return "qna/detail";
	}
	
	//문의글 수정화면 요청
	//제목 공백 글자 뒤에 제거되는 문제가 있답니다...
	@RequestMapping("modify.qna")
	public String deleteQna(Model model,
							@RequestParam int id) {
		model.addAttribute("data", noticeService.qna_detail(id));
		return "qna/modify";
	}
	
	//문의글 수정저장 처리
	@RequestMapping("update.qna")
	public String updateQna(HttpSession session,
							Model model,
							Qna qna) {
		noticeService.qna_update(qna);
		model.addAttribute("id", qna.getId());
		model.addAttribute("url", "detail.qna");
		return "redirect:detail.qna";
	}
}
