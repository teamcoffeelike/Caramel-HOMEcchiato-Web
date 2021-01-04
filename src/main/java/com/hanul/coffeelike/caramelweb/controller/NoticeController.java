package com.hanul.coffeelike.caramelweb.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanul.coffeelike.caramelweb.data.Notice;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.service.NoticeService;
import com.hanul.coffeelike.caramelweb.data.Qna;
import com.hanul.coffeelike.caramelweb.data.UserProfileData;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	//공지글목록 화면 요청
	@RequestMapping("/notice")
	public String notice(HttpSession session,
			Model model,
			@RequestParam(defaultValue = "1") Integer index) {
		Page page = new Page();
		page.setTotalCount(noticeService.totalCount());
		int maximumPage = page.getMaximumPage(10);
		
		if(index>maximumPage) {
			// 인덱스 초과, 특수 처리
			return "123";
		}
		
		page.setCurrentPage(index);
		
		// service로 넘김
		List<Notice> notices = noticeService.getNotice(page);
		model.addAttribute("page", page);
		model.addAttribute("notices", notices);
		return "notice/list";
	}

	//신규공지글 작성화면 요청
	@RequestMapping("/new")
	public String newNotice() {
		return "notice/new";
	}
	
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insertNotice(HttpSession session, Notice notice) {
		noticeService.insertNotice(notice);
		return "redirect:notice";
	}
	
	//공지글 상세화면 요청
	@RequestMapping("/detail.no")
	public String detailNotice(Model model, int id) {
		noticeService.detailNotice(id);
		model.addAttribute("data", noticeService.detailNotice(id));
		return "notice/detail";
	}
	 
	//공지글 수정화면 요청
	@RequestMapping("/modify.no")
	public String modifyNotice(Model model, int id) {
		model.addAttribute("data", noticeService.detailNotice(id));
		return "notice/modify";
	}
	
	//공지글 수정저장처리 요청
	@RequestMapping("/update.no")
	public String updateNotice(HttpSession session,
							   Model model,
							   Notice notice) {
		System.out.println("id "+notice.getId()+" "+notice.getTitle()+" "+notice.getContent());
		
		noticeService.updateNotice(notice);
		return "redirect:detail.no?id=" + notice.getId();
	}
	
	//공지글 삭제처리 요청
	@RequestMapping("/delete.no")
	public String deleteNotice(int id) {
		noticeService.deleteNotice(id);
		return "redirect:notice";
	}
}
