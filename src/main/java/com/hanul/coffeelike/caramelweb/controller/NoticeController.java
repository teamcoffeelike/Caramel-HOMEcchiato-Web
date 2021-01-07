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

@Controller
public class NoticeController {
	public static final int NOTICE_PAGE_LIST_SIZE = 10;
	
	@Autowired
	private NoticeService noticeService;
	
	//공지글목록 화면 요청
	@RequestMapping("/notice")
	public String notice(HttpSession session,
	                     Model model,
	                     @RequestParam(required = false) @Nullable String search,
	                     @RequestParam(required = false) @Nullable String keyword,
	                     @RequestParam(defaultValue = "1") int currentPage){
		Page page = new Page(noticeService.totalCount(search, keyword),
				currentPage,
				search,
				keyword,
				NOTICE_PAGE_LIST_SIZE);
		
		if(page.getCurrentPage()>page.getMaximumPage()) {
			// 인덱스 초과, 특수 처리
			return "noticeRequired"; 
		}
		
		page.setCurrentPage(currentPage);
		
		// service로 넘김
		List<Notice> notices = noticeService.getNotice(page);
		model.addAttribute("page", page);
		model.addAttribute("notices", notices);
		return "notice/list";
	}
	
	// 상세화면에서 목록으로 누를시 조회한 페이지 인덱스 유지
	@RequestMapping("/noticeBack")
	public String noticeBack(Model model,
	                         @RequestParam(required = false) @Nullable String search,
	                         @RequestParam(required = false) @Nullable String keyword,
	                         @RequestParam int from){
		Page page = new Page(noticeService.totalCount(search, keyword),
				1,
				search,
				keyword,
				NOTICE_PAGE_LIST_SIZE);
		
		int index = noticeService.getIndex(from, search, keyword);
		
		StringBuilder stb = new StringBuilder("redirect:notice?currentPage=")
				.append(page.getPageOf(index));
		
		if(search!=null)
			stb.append("&search=").append(search);
		
		if(keyword!=null)
			stb.append("&keyword=").append(keyword);
		
		return stb.toString();
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
	public String detailNotice(Model model,
	                           @RequestParam(required = false) @Nullable String search,
	                           @RequestParam(required = false) @Nullable String keyword,
	                           int id){
		Notice notice = noticeService.detailNotice(id);
		model.addAttribute("search", search);
		model.addAttribute("keyword", keyword);
		model.addAttribute("data", notice);
		model.addAttribute("crlf", "\r\n");
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
