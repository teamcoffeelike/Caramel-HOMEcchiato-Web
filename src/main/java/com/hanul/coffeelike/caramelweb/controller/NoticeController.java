package com.hanul.coffeelike.caramelweb.controller;

import java.util.List;

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
}
